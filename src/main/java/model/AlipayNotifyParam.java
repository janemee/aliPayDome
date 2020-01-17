package model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName AlipayNotifyParam
 * @Description 支付宝支付请求参数包装类
 * @Author jzm
 * @Date : 2020/1/14 18:18
 **/
@Data
public class AlipayNotifyParam {
    /**
     * 编码格式
     */
    private String charset;
    /**
     * 商户网站唯一订单号
     */
    private String outTradeNo;
    /**
     * 该交易在支付宝系统中的交易流水号。最长64位
     */
    private String tradeNo;
    /**
     * 方法名
     */
    private String method;
    /**
     * 该笔订单的资金总额
     */
    private BigDecimal totalAmount;
    /**
     * 签名结果
     */
    private String sign;
    /**
     * 授权的APPID
     */
    private String auth_app_id;
    /**
     * 支付宝分配给开发者的应用Id
     */
    private String appId;
    /**
     * 签名类型
     */
    private String signType;
    /**
     * 收款支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字
     */
    private String sellerId;
    /**
     * 时间
     */
    private String timestamp;

}
