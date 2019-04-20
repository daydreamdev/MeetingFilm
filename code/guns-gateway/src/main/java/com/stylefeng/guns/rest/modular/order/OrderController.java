package com.stylefeng.guns.rest.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.baomidou.mybatisplus.plugins.Page;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.stylefeng.guns.api.alipay.AliPayServiceAPI;
import com.stylefeng.guns.api.alipay.vo.AliPayInfoVO;
import com.stylefeng.guns.api.alipay.vo.AliPayResultVO;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.core.util.TokenBucket;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/order/")
public class OrderController {

    private static TokenBucket tokenBucket = new TokenBucket();
    private static final String img_pre = "http://119.3.214.253:8899/tempBack";

    @Reference(interfaceClass = OrderServiceAPI.class, check = false)
    private OrderServiceAPI orderServiceAPI;

    @Reference(interfaceClass = AliPayServiceAPI.class, check = false)
    private AliPayServiceAPI aliPayServiceAPI;

/*    public ResponseVO error(Integer fieldId, String soldSeats, String seatsName) {
        return ResponseVO.serviceFail("抱歉，下单的人太多了，请稍后重试");
    }*/

    // 购票
    /*
        信号量隔离
        线程池隔离
        线程切换
     */
/*    @HystrixCommand(fallbackMethod = "error", commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")},
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "1"),
                    @HystrixProperty(name = "maxQueueSize", value = "10"),
                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "1000"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "8"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1500")
            })*/
    @RequestMapping(value = "buyTickets", method = RequestMethod.POST)
    public ResponseVO buyTickets(Integer fieldId, String soldSeats, String seatsName) {

        if (tokenBucket.getToken()) {
            // 验证售出的票是否为真
            boolean isTrue = orderServiceAPI.isTrueSeats(fieldId + "", soldSeats);

            // 已经销售的座位里，有没有这些座位
            boolean isNotSold = orderServiceAPI.isNotSoldSeats(fieldId + "", soldSeats);

            // 验证，上述两个内容有一个不为真，则不创建订单信息
            if (isTrue && isNotSold) {
                // 创建订单信息,注意获取登陆人
                String userId = CurrentUser.getCurrentUser();
                if (userId == null || userId.trim().length() == 0) {
                    return ResponseVO.serviceFail("用户未登陆");
                }
                OrderVO orderVO = orderServiceAPI.saveOrderInfo(fieldId, soldSeats, seatsName, Integer.parseInt(userId));
                if (orderVO == null) {
                    log.error("购票未成功");
                    return ResponseVO.serviceFail("购票业务异常");
                } else {
                    return ResponseVO.success(orderVO);
                }
            } else {
                return ResponseVO.serviceFail("订单中的座位编号有问题");
            }
        } else {
            return ResponseVO.serviceFail("购票人数过多，请稍后再试");
        }
    }


    @RequestMapping(value = "getOrderInfo", method = RequestMethod.POST)
    public ResponseVO getOrderInfo(
            @RequestParam(name = "nowPage", required = false, defaultValue = "1") Integer nowPage,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize
    ) {

        // 获取当前登陆人的信息
        String userId = CurrentUser.getCurrentUser();

        // 使用当前登陆人获取已经购买的订单
        Page<OrderVO> page = new Page<>(nowPage, pageSize);
        if (userId != null && userId.trim().length() > 0) {
            Page<OrderVO> result = orderServiceAPI.getOrderByUserId(Integer.parseInt(userId), page);
            // 合并结果
            int totalPages = (int) result.getPages();

            List<OrderVO> orderVOList = new ArrayList<>();
            orderVOList.addAll(result.getRecords());
            return ResponseVO.success(nowPage, totalPages, "", orderVOList);

        } else {
            return ResponseVO.serviceFail("用户未登陆");
        }
    }


    @RequestMapping(value = "getPayInfo", method = RequestMethod.POST)
    public ResponseVO getPayInfo(@RequestParam("orderId") String orderId) {
        // 获取当前登陆人的信息
        String userId = CurrentUser.getCurrentUser();
        if (userId == null || userId.trim().length() == 0) {
            return ResponseVO.serviceFail("抱歉，用户未登陆");
        }
        // 订单二维码返回结果
        AliPayInfoVO aliPayInfoVO = aliPayServiceAPI.getQRCode(orderId);
        return ResponseVO.success(img_pre, aliPayInfoVO);
    }


    @RequestMapping(value = "getPayResult", method = RequestMethod.POST)
    public ResponseVO getPayResult(
            @RequestParam("orderId") String orderId,
            @RequestParam(name = "tryNums", required = false, defaultValue = "1") Integer tryNums) {
        // 获取当前登陆人的信息
        String userId = CurrentUser.getCurrentUser();
        if (userId == null || userId.trim().length() == 0) {
            return ResponseVO.serviceFail("抱歉，用户未登陆");
        }

        // 将当前登陆人的信息传递给后端
        RpcContext.getContext().setAttachment("userId", userId);

        // 判断是否支付超时
        if (tryNums >= 4) {
            return ResponseVO.serviceFail("订单支付失败，请稍后重试");
        } else {
            AliPayResultVO aliPayResultVO = aliPayServiceAPI.getOrderStatus(orderId);
            if (aliPayResultVO == null || ToolUtil.isEmpty(aliPayResultVO.getOrderId())) {
                AliPayResultVO serviceFailVO = new AliPayResultVO();
                serviceFailVO.setOrderId(orderId);
                serviceFailVO.setOrderStatus(0);
                serviceFailVO.setOrderMsg("支付不成功");
                return ResponseVO.success(serviceFailVO);
            }
            return ResponseVO.success(aliPayResultVO);
        }
    }

}
