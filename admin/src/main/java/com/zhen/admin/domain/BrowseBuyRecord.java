package com.zhen.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BrowseBuyRecord {
    private Long productId;
    private Long buyerId;
    private Character action;
    private LocalDateTime actionTime;
}
