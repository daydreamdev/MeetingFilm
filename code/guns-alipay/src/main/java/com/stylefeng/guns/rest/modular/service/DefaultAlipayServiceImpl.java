package com.stylefeng.guns.rest.modular.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.stylefeng.guns.api.alipay.AliPayServiceAPI;
import com.stylefeng.guns.api.alipay.vo.AliPayInfoVO;
import com.stylefeng.guns.api.alipay.vo.AliPayResultVO;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.rest.common.util.FTPUtil;
import com.stylefeng.guns.rest.modular.alipay.config.Configs;
import com.stylefeng.guns.rest.modular.alipay.model.ExtendParams;
import com.stylefeng.guns.rest.modular.alipay.model.GoodsDetail;
import com.stylefeng.guns.rest.modular.alipay.model.builder.AlipayTradePrecreateRequestBuilder;
import com.stylefeng.guns.rest.modular.alipay.model.builder.AlipayTradeQueryRequestBuilder;
import com.stylefeng.guns.rest.modular.alipay.model.result.AlipayF2FPrecreateResult;
import com.stylefeng.guns.rest.modular.alipay.model.result.AlipayF2FQueryResult;
import com.stylefeng.guns.rest.modular.alipay.service.AlipayMonitorService;
import com.stylefeng.guns.rest.modular.alipay.service.AlipayTradeService;
import com.stylefeng.guns.rest.modular.alipay.service.impl.AlipayMonitorServiceImpl;
import com.stylefeng.guns.rest.modular.alipay.service.impl.AlipayTradeServiceImpl;
import com.stylefeng.guns.rest.modular.alipay.service.impl.AlipayTradeWithHBServiceImpl;
import com.stylefeng.guns.rest.modular.alipay.utils.Utils;
import com.stylefeng.guns.rest.modular.alipay.utils.ZxingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Service(interfaceClass = AliPayServiceAPI.class,
        mock = "com.stylefeng.guns.api.alipay.AliPayServiceMock")
public class DefaultAlipayServiceImpl implements AliPayServiceAPI {

    @Reference(interfaceClass = OrderServiceAPI.class, check = false)
    private OrderServiceAPI orderServiceAPI;

    @Autowired
    private FTPUtil ftpUtil;

    // 支付宝当面付2.0服务
    private static AlipayTradeService tradeService;

    // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
    private static AlipayTradeService tradeWithHBService;

    // 支付宝交易保障接口服务，供测试接口api使用，请先阅读readme.txt
    private static AlipayMonitorService monitorService;

    static {
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

        // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
        tradeWithHBService = new AlipayTradeWithHBServiceImpl.ClientBuilder().build();

        /** 如果需要在程序中覆盖Configs提供的默认参数, 可以使用ClientBuilder类的setXXX方法修改默认参数 否则使用代码中的默认设置 */
        monitorService = new AlipayMonitorServiceImpl.ClientBuilder()
                .setGatewayUrl("http://mcloudmonitor.com/gateway.do").setCharset("GBK")
                .setFormat("json").build();
    }

    // 获取二维码地址
    @Override
    public AliPayInfoVO getQRCode(String orderId) {

        // 获取二维码地址
        String filePath = trade_precreate(orderId);
        // 如果地址为空，则表示获取二维码不成功
        if (filePath == null || filePath.trim().length() == 0) {
            return null;
        } else {
            AliPayInfoVO aliPayInfoVO = new AliPayInfoVO();
            aliPayInfoVO.setOrderId(orderId);
            aliPayInfoVO.setQRCodeAddress(filePath);
            return aliPayInfoVO;
        }
    }

    // 获取订单支付状态
    @Override
    public AliPayResultVO getOrderStatus(String orderId) {
        // 看看是否有当前登陆人
//        String userId = RpcContext.getContext().getAttachment("userId");
//
//        log.info("DefaultAlipayServiceImpl - getOrderStatus - userId = "+userId);

        // 获取订单支付状态
        boolean isSuccess = trade_query(orderId);
        if (isSuccess) {
            AliPayResultVO aliPayResultVO = new AliPayResultVO();
            aliPayResultVO.setOrderId(orderId);
            aliPayResultVO.setOrderStatus(1);
            aliPayResultVO.setOrderMsg("支付成功");
            return aliPayResultVO;
        }
        return null;
    }

    // 测试当面付2.0查询订单
    public boolean trade_query(String orderId) {
        boolean flag = false;
        // (必填) 商户订单号，通过此商户订单号查询当面付的交易状态
        String outTradeNo = orderId;

        // 创建查询请求builder，设置请求参数
        AlipayTradeQueryRequestBuilder builder = new AlipayTradeQueryRequestBuilder()
                .setOutTradeNo(outTradeNo);

        AlipayF2FQueryResult result = tradeService.queryTradeResult(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("查询返回该订单支付成功: )");

                // 当订单支付成功状态时，修改订单状态为1
                flag = orderServiceAPI.paySuccess(orderId);

                break;

            case FAILED:
                log.error("查询返回该订单支付失败或被关闭!!!");
                break;

            case UNKNOWN:
                log.error("系统异常，订单支付状态未知!!!");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
        return flag;
    }


    // 测试当面付2.0生成支付二维码
    public String trade_precreate(String orderId) {
        String filePath = "";
        // 获取订单信息
        OrderVO orderVO = orderServiceAPI.getOrderInfoById(orderId);

        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = orderId;

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "Meeting院线购票业务";

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = orderVO.getOrderPrice();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = "购买电影票共花费" + orderVO.getOrderPrice();

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "jiangzh";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "wangfei";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
//        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
//        GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx小面包", 1000, 1);
//        // 创建好一个商品后添加至商品明细列表
//        goodsDetailList.add(goods1);
//
//        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
//        GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
//        goodsDetailList.add(goods2);

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                //                .setNotifyUrl("http://www.test-notify-url.com")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();

                // 需要修改为运行机器上的路径
                filePath = String.format("C:/Users/29212/Desktop/temp/qr-%s.png",
                        response.getOutTradeNo());
                String fileName = String.format("qr-%s.png", response.getOutTradeNo());
                log.info("filePath:" + filePath);
                File qrCodeImge = ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);

                boolean isSuccess = ftpUtil.uploadFile(fileName, qrCodeImge);
                if (!isSuccess) {
                    filePath = "";
                    log.error("二维码上传失败");
                }

                break;

            case FAILED:
                log.error("支付宝预下单失败!!!");
                break;

            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
        return filePath;
    }

}
