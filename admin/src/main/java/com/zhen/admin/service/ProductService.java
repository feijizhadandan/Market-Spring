package com.zhen.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhen.admin.domain.Product;
import com.zhen.common.domain.AjaxResult;

import javax.servlet.http.HttpServletRequest;

public interface ProductService extends IService<Product> {
    AjaxResult getAllProduct();

    AjaxResult getProductDetail(Long id);

    AjaxResult addNewProduct(Product product, HttpServletRequest request);

    AjaxResult updateProduct(Product product, HttpServletRequest request);

    AjaxResult deleteProduct(Long id, HttpServletRequest request);

    AjaxResult getShowProduct();

    AjaxResult getShowProductDetail(Long id);
}
