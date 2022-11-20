package com.zhen.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BrowseRecord {
    private Long productId;
    private Long buyerId;
    private LocalDateTime actionTime;
}
