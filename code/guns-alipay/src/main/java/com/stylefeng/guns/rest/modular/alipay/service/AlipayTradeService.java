package com.stylefeng.guns.rest.modular.alipay.service;

import com.stylefeng.guns.rest.modular.alipay.model.builder.AlipayTradePayRequestBuilder;
import com.stylefeng.guns.rest.modular.alipay.model.builder.AlipayTradePrecreateRequestBuilder;
import com.stylefeng.guns.rest.modular.alipay.model.builder.AlipayTradeQueryRequestBuilder;
import com.stylefeng.guns.rest.modular.alipay.model.builder.AlipayTradeRefundRequestBuilder;
import com.stylefeng.guns.rest.modular.alipay.model.result.AlipayF2FPayResult;
import com.stylefeng.guns.rest.modular.alipay.model.result.AlipayF2FPrecreateResult;
import com.stylefeng.guns.rest.modular.alipay.model.result.AlipayF2FQueryResult;
import com.stylefeng.guns.rest.modular.alipay.model.result.AlipayF2FRefundResult;

/**
 * Created by liuyangkly on 15/7/29.
 */
public interface AlipayTradeService {

    // 当面付2.0流程支付
    public AlipayF2FPayResult tradePay(AlipayTradePayRequestBuilder builder);

    // 当面付2.0消费查询
    public AlipayF2FQueryResult queryTradeResult(AlipayTradeQueryRequestBuilder builder);

    // 当面付2.0消费退款
    public AlipayF2FRefundResult tradeRefund(AlipayTradeRefundRequestBuilder builder);

    // 当面付2.0预下单(生成二维码)
    public AlipayF2FPrecreateResult tradePrecreate(AlipayTradePrecreateRequestBuilder builder);
}
