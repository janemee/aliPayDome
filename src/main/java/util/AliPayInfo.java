package util;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AliPayInfo {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 支付金额
     */
    private BigDecimal amount;

    /**
     * 系统支付订单号
     */
    private String orderNo;

    public AliPayInfo() {
    }


    public AliPayInfo(Integer userId, BigDecimal amount, String orderNo) {
        this.userId = userId;
        this.amount = amount;
        this.orderNo = orderNo;
    }
}
