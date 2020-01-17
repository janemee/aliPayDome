package controller;


import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.xiaoleilu.hutool.http.HtmlUtil;
import config.AliPayConfig;
import model.BussinessException;
import model.ResultEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import util.AliPayInfo;
import util.DateUtils;
import util.OrderNoUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;


@RestController
@RequestMapping("/prod/aliPay")
public class AliPayController extends GenericController {
    private static final Logger log = LoggerFactory.getLogger(AliPayController.class);


    /**
     * 支付宝 充值
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/pay")
    public ResultEntity pay(String userUuid, BigDecimal amount) {
        try {
            Map data = new HashMap(1);
            //服务器接口地址
            String serviceUrl = "";
            // 获得初始化的aliPayClient
            AlipayClient alipayClient = new DefaultAlipayClient(
                    AliPayConfig.gatewayUrl,
                    AliPayConfig.APPID,
                    AliPayConfig.RSA_PRIVATE_KEY,
                    AliPayConfig.FORMAT,
                    AliPayConfig.CHARSET,
                    AliPayConfig.ALIPAY_PUBLIC_KEY,
                    AliPayConfig.SIGNTYPE);
            // 创建API对应的request
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            //订单参数, 注释部分订单信息来自于前端,这样可免去数据库查询,暂时不实现这个设计
            //商户订单号，商户网站订单系统中唯一订单号，必填
            //生成充值订单号
            String outOrderNo = "CZ" + OrderNoUtils.getSerialNumber();
            //订单名称，必填  格式：系统名称 + 操作类型
            String subject = "项目名称" + " - 账户充值";
            // 付款金额,单位：元，必填
            String totalAmount = amount.toString();
            // 商品描述，可空
            String body = subject + "-充值金额：" + totalAmount;
            //绝对超时时间，格式为yyyy-MM-dd HH:mm:ss
            String timeoutExpress = DateUtils.dateStr(DateUtils.rollMinute(new Date(), 3), "yyyy-MM-dd HH:mm:ss");
            //设置支付请求参数
            AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
            model.setOutTradeNo(outOrderNo);
            model.setSubject(subject);
            model.setTotalAmount(totalAmount);
            model.setBody(body);
            model.setTimeoutExpress(timeoutExpress);
            model.setProductCode(AliPayConfig.productCode);
            alipayRequest.setBizModel(model);
            //同步回调地址
            alipayRequest.setReturnUrl(serviceUrl + AliPayConfig.return_url);
            //异步回调地址
            alipayRequest.setNotifyUrl(serviceUrl + AliPayConfig.notify_url);
            // 前端提交表单   调用SDK生成表单
            String form = alipayClient.pageExecute(alipayRequest).getBody();
            // 直接将完整的表单html输出到页面
            log.info("支付宝支付生成请求htmlStr - from: " + form);
            //返回数据表单给前端
            data.put("from", form);
            //添加充值记录

            //将支付信息存入缓存

            return ResultEntity.success("支付授权成功", data);
        } catch (Exception e) {
            throw new BussinessException("支付授权失败，失败原因：" + e.getMessage());
        }
    }

    /**
     * 支付宝支付回调处理
     *
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/aliPayNotify")
    public void aliPayNotify(HttpServletRequest request, HttpServletResponse response) {
        log.info("#################################支付宝支付--异步回调######################################");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            Map<String, String> responseMap = getParams(request);
            boolean verifyResult = AlipaySignature.rsaCheckV1(responseMap, AliPayConfig.ALIPAY_PUBLIC_KEY, AliPayConfig.CHARSET, AliPayConfig.SIGNTYPE);
            if (verifyResult) {
                String tradeStatus = responseMap.get("trade_status");
                String outTradeNo = responseMap.get("out_trade_no");
                String tradeMsg = responseMap.get("tradeMsg");
                //根据返回的订单号 查询缓存中的信息
                String redisSonStr = "根据订单号  获取本地支付缓存信息";
                if (StringUtils.isBlank(redisSonStr)) {
                    throw new BussinessException("订单不存在或已完成支付");
                }
                //本地支付缓存信息
                AliPayInfo aliPayInfo = JSON.parseObject(redisSonStr, AliPayInfo.class);

                //支付成功 更新订单信息 添加账户和流水
                if (tradeStatus.equals(AliPayConfig.TRADE_SUCCESS)) {
                    //支付金额
                    BigDecimal payAmount = aliPayInfo.getAmount();
                    //手续费
                    BigDecimal tradeFee = BigDecimal.ZERO;
                    //获取充值用户信息
                    //更新
                    //记录资金
                    //更新资金
                    //清楚订单缓存
                } else {
                    log.info("支付失败,状态码:" + tradeStatus);
                    //支付失败处理
                    String msg = "支付失败，失败原因:" + tradeMsg;
                    log.error(msg);
                    //更新
                    //清楚订单缓存
                    log.info("#################################支付宝支付--异步回调处理失败######################################");
                }
                writer.println("success");
            } else {
                log.info("#################################支付宝支付--异步回调处理失败######################################");
                //验签失败
                String msg = "验签失败";
                log.error(msg);
                writer.println("fail");

            }
        } catch (Exception e) {
            writer.println("fail");
            log.info("#################################支付宝支付--异步回调处理失败######################################");
        }
        log.info("#################################支付宝支付--异步回调处理成功######################################");
        writer.flush();
        writer.close();
    }


    private Map<String, String> getParams(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Object o : requestParams.keySet()) {
            String name = (String) o;
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }


    /**
     * 网关测试dome
     *
     * @param str
     * @throws Exception
     */
    public static void main(String str[]) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient(
                AliPayConfig.gatewayUrl, AliPayConfig.APPID, AliPayConfig.RSA_PRIVATE_KEY,
                AliPayConfig.FORMAT, AliPayConfig.CHARSET, AliPayConfig.ALIPAY_PUBLIC_KEY, AliPayConfig.SIGNTYPE);
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setBizContent("{" +
                "\"body\":\"Iphone6 16G\"," +
                "\"subject\":\"大乐透\"," +
                "\"out_trade_no\":\"70501111111S001111119\"," +
                "\"total_amount\":9.00," +
                "\"quit_url\":\"http://www.taobao.com/product/113714.html\"," +
                "\"product_code\":\"QUICK_WAP_WAY\"," +
                "\"sub_merchant\":{" +
                "\"merchant_id\":\"19023454\"," +
                "\"merchant_type\":\"alipay: 支付宝分配的间连商户编号, merchant: 商户端的间连商户编号\"" +
                "    }," +
                "\"merchant_order_no\":\"20161008001\"," +
                "\"enable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
                "\"disable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
                "\"store_id\":\"NJ_001\"," +
                "\"settle_info\":{" +
                "        \"settle_detail_infos\":[{" +
                "          \"trans_in_type\":\"cardAliasNo\"," +
                "\"trans_in\":\"A0001\"," +
                "\"summary_dimension\":\"A0001\"," +
                "\"settle_entity_id\":\"2088xxxxx;ST_0001\"," +
                "\"settle_entity_type\":\"SecondMerchant、Store\"," +
                "\"amount\":0.1" +
                "          }]" +
                "    }");
        System.out.println(HtmlUtil.removeHtmlTag(request.getBizContent()));
        AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);
        System.out.println(response.toString());
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }
}


