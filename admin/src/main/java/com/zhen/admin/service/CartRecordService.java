package com.zhen.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhen.admin.domain.CartRecord;

import com.zhen.admin.dto.CartDto;
import com.zhen.common.domain.AjaxResult;

import javax.servlet.http.HttpServletRequest;

public interface CartRecordService extends IService<CartRecord> {
    AjaxResult getBuyerCart(HttpServletRequest request);

    AjaxResult addCartRecord(CartDto addCartDto, HttpServletRequest request);

    AjaxResult changeCartRecord(CartDto changeCartDto, HttpServletRequest request);

    AjaxResult deleteCartRecord(Long productId, HttpServletRequest request);
}
