package com.zhen.admin.vo;

import lombok.Data;

@Data
public class CartVo {
    private Long code;
    private String productName;
    private Double productPrice;
    private Integer count;
    private Double totalPrice;
}
