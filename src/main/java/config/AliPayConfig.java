package config;

/**
 * 支付宝支付 相关配置信息类
 */
public class AliPayConfig {

    /**
     * 正式商户APP_ID / 沙箱环境
     */
  public static String APPID = "2019100968175572";

    /**
     * 私钥
     */
    public static String  RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCjkf3xnC/BELTY6blxnz4qD/bYE/W3Ln8rYSM/elvsf+l2he1QoH4JZcRy8+wbOUnIi8lVv9AzsuxbcBRet2zT7lZ2Ej5/FhNeFJtPVxEf5z0npn050RzPf0YKxcTyxolct6RSTWSagpftEEZXfq1kZnx293w26w/VgRw2SMOO4GmdWTOlN2NnEO9zWZ9rBt+qPAylVhDv6sriBWv2o6cBHCTeQLYyBAOPC6B1J4dcy1o/g8Vv4zGIYdFzuqJMDLygvM/KNmCFnZwkSDw7axuFZj7cy2TdgQOf1kLp82dhtTVxCpl7pnWw5KXSJcx92VtyqhXpaMe+gbi7RHYF96qXAgMBAAECggEABz7VR9mKR/wMdrtLqpvKLiTM419m9/lISsyfuhUac0xrNAPUskiZrKLkY88oO0B7YzXF8lVvzIp1a8t4K4XmfTDGha5jNE/vBbDV/Fq3+HjOva3jSCdaJlrV6G5APhzLQt8jmT2yYiG8xxR8eS20UokzpDVsPCaHagaxEDqC5xY5jxBZHXKZOKJnNqzEnk5ONy9olx46Wq+UnGgh6sCPid2Nam2V/kvnj/164GnW9tQAAW0k27ZPnCiVjoGh/4HeVpvi31sY8+pnMcrHh3nSMkOwyjzqoiyTdkFw3jxvhxI0C6OtDXZvH1Ub8xdqyf2TOr5mMlq9bzJbj0dXZ4uJ0QKBgQDYgmpj6ZZUjLK8jhFePCT6TSxPZOpJBr4cMssq1gmzv+4N+20jwYUnjymEamqNeb37Wl0XykUzk9QNZirO3HNdX68kigSWHmjt+QKoEU+SrdRGdFHGKhxqVcCWRYTaMMnRouYgAqAdr1U2mdiXEWGTqeu7TBg+eLYMwGddElAMPwKBgQDBZ6lk9Zd5n8IgGv0B3CwDZCY/A1M5UunmgaiiDWxVR0G60rBC0a8b2sD2v85nQ+HSd7NlWSVqSvUksgJr5RKju0HrhBlsqWar2+htbc1ujjXmmeJlBR8kMe4+tF3ROZXXKiTEJyWCcYjLtToVSd4OIJuz4QvFVPtQH6U3Yq8rqQKBgQDRbgW/s9oo2xih78K5JvCV9kSgX/uIvC71U+TXAMfNyiBaAhVmevF0fLUqU03SoCq3koduVchuxdZfR66s/u3RKpdefutE0xGo8DAzptsUBXXK2QWo7F8kNOLf+UszS/JNCIgV+rQrQsjo0hSCvNtoXPub51WkSFvBJfRWQTqRPQKBgDWcvblN1xKQ9agesxnr5Yt/HJcFHfHbCRmrwYpCfFBbc14Nf2zWYVswVaNQ4i+AAr5sDh+/CX8gjjoA2mj8VCFtDEX0FXQotVxYlmKXOIY4B7sH11k3hT1sVeWt6//OnbnKZey2CVDSliAZ+aLjGhoCcgb4EGJAP3Yjf5IN2g8ZAoGAerVp3e1HhYksXtK+vV1sh2cF79WjUsdPmPKl5/vATC99nWZPPMjYwnoLDAHiC4OxTzvsle/lK3U+e13Gekp27CAJImuxSp3/DsWcGao29uYhmY519O1jv7aU2t+tkMu4xkcRujPNQQcMvjjcFAsUW9iTsWvxfps/5lqqu/46wAM=";

    /**
     * 正式支付宝公钥
     */
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjZ9s5seQwgRsvtc41vyWIZfGpX7a+heZxixEmxsX6BTwsxf1CrZjT9k18NCjfcaASAQy9DsjFHd9mmo/iippOUDynFJZ2jraPHpza+xWfnlfkoq5QgI14B4mwcBZjkvqOqPOOUcg79FDFY9Gyih8iPj31dcaNpvWGQk2uJ87XczUOASXErpU7aWsHxSR/7cKGifWLUJ6d+m21+muvyf0eSSoe+XKhbBJsz0fxBN8LX92GInWZbjZM89OacEPS3MUNAFhTALRoBe+raPqV5kBapeQK3IVH8qm4wcgoYb2JVjHUw5ngBd4sAx6196wdpsUrBEe8ZEes1B/d3IkKN6siwIDAQAB";


    /**
     * 异步回调地址
     */
    public static String notify_url = "/api/web/aliPay/aliPayNotify";

    /**
     * 同步回调地址 支付成功或者失败后以及返回商户的url 前端页面地址
     */
    public static String return_url = "/my/my";

    /**
     * 支付宝网关 正式环境请求地址 注意dev是沙箱环境
     */
     public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";

    /**
     * 数据格式
     */
    public static String FORMAT = "JSON";

    /**
     * 系统支付缓存地址
     */
    public static String PAY_REDIS_URL = "pay:aliPay:recharge:";

    /**
     * 销售产品码，与支付宝签约的产品码名称。注：目前仅支持FAST_INSTANT_TRADE_PAY
     */
    public static String productCode = "FAST_INSTANT_TRADE_PAY";

    /**
     * 支付操作结果 操作成功标识 （回调时需要校验支付结果）
     */
    public static String Success = "Success";

    /**
     * 加密方式
     */
    public static String SIGNTYPE = "RSA2";
    /**
     * 字符集
     */
    public static String CHARSET = "utf-8";

    /**
     * 支付成功状态码
     */
    public static String TRADE_SUCCESS = "TRADE_SUCCESS";
}
