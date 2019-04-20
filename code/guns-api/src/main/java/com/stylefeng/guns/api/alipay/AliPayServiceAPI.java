package com.stylefeng.guns.api.alipay;

import com.stylefeng.guns.api.alipay.vo.AliPayInfoVO;
import com.stylefeng.guns.api.alipay.vo.AliPayResultVO;

public interface AliPayServiceAPI {

    AliPayInfoVO getQRCode(String orderId);

    AliPayResultVO getOrderStatus(String orderId);

}
