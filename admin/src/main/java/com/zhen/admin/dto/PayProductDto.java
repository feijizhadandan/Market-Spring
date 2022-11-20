package com.zhen.admin.dto;

import lombok.Data;

/**
 * 购物车中支付的条目
 */
@Data
public class PayProductDto {
    private Long id;
    private String productName;
    private Double productPrice;
    private Integer count;
    private Double totalPrice;
}
