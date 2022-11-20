package com.zhen.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BuyRecord {
    private Long buyerId;
    private Long productId;
    private Integer count;
    private LocalDateTime actionTime;
}
