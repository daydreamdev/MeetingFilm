package com.stylefeng.guns.api.alipay;

import com.stylefeng.guns.api.alipay.vo.AliPayInfoVO;
import com.stylefeng.guns.api.alipay.vo.AliPayResultVO;

/*
    业务降级方法
 */
public class AliPayServiceMock implements AliPayServiceAPI{
    @Override
    public AliPayInfoVO getQRCode(String orderId) {
        return null;
    }

    @Override
    public AliPayResultVO getOrderStatus(String orderId) {

        AliPayResultVO aliPayResultVO = new AliPayResultVO();
        aliPayResultVO.setOrderId(orderId);
        aliPayResultVO.setOrderStatus(0);
        aliPayResultVO.setOrderMsg("尚未支付成功");

        return aliPayResultVO;
    }
}
