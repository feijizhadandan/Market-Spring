package com.zhen.admin.controller.product;

import com.zhen.admin.domain.Product;
import com.zhen.admin.service.ProductService;
import com.zhen.common.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/product/buyer")
public class ProductBuyerController {

    @Autowired
    private ProductService productService;

    /**
     * 展示用户可见商品
     * @return 相应消息
     */
    @GetMapping()
    @PreAuthorize("hasAuthority('system:buy')")
    public AjaxResult getShowProduct() {
        return productService.getShowProduct();
    }

    /**
     * 展示用户可见商品的详细信息
     * @param id 商品的 id
     * @return 相应消息
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:buy')")
    public AjaxResult getShowProductDetail(@PathVariable Long id) {
        return productService.getShowProductDetail(id);
    }

}
