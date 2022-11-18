package com.zhen.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhen.admin.domain.Product;
import com.zhen.admin.mapper.ProductMapper;
import com.zhen.admin.service.ProductService;
import com.zhen.common.domain.AjaxResult;
import com.zhen.framework.security.domain.User;
import com.zhen.framework.security.service.impl.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private TokenService tokenService;

    @Override
    public AjaxResult getAllProduct() {
        List<Product> products = productMapper.selectList(new QueryWrapper<>());
        return AjaxResult.success(products);
    }

    @Override
    public AjaxResult getProductDetail(Long id) {
        Product product = productMapper.selectById(id);
        return AjaxResult.success(product);
    }

    @Override
    public AjaxResult addNewProduct(Product product, HttpServletRequest request) {
        // 检查有无同名商品
        String productName = product.getProductName();
        Product sameNameProduct = productMapper.selectOne(
                new LambdaQueryWrapper<Product>().eq(Product::getProductName, productName)
        );
        if (sameNameProduct != null) {
            return AjaxResult.error("存在同名商品");
        }
        User loginUser = tokenService.getLoginUserDetail(request);
        product.setUpdateBy(loginUser.getId());
        product.setCreateBy(loginUser.getId());
        productMapper.insert(product);
        return AjaxResult.success("添加商品成功");
    }

    @Override
    public AjaxResult updateProduct(Product product, HttpServletRequest request) {
        // 获取旧 version 值（要让乐观锁生效，就要获取修改前数据库中的最新 version 值）
        Long oldVersion = productMapper.selectById(product.getId()).getVersion();
        // 修改更新人
        product.setUpdateBy(tokenService.getLoginUserDetail(request).getId());
        product.setVersion(oldVersion);
        productMapper.updateById(product);
        return AjaxResult.success("更新商品数据成功");
    }

    @Override
    public AjaxResult deleteProduct(Long id, HttpServletRequest request) {
        // 直接用 delete 删除，会导致 updateTime、updateBy、version都没更新
        // 如果用普通的 update 的话，不能成功修改 delFlag
        // 用 updateWrapper：能自动更新 updateTime (不能自动更新 version ) delFlag 自行修改
        LambdaUpdateWrapper<Product> deleteWrapper = new LambdaUpdateWrapper<>();
        deleteWrapper.eq(Product::getId, id)
                .set(Product::getDelFlag, 2)
                .set(Product::getUpdateBy, tokenService.getLoginUserDetail(request).getId());
        productMapper.update(new Product(), deleteWrapper);

        return AjaxResult.success("删除商品成功");
    }

    @Override
    public AjaxResult getShowProduct() {
        List<Product> products = productMapper.selectList(
                new LambdaQueryWrapper<Product>().eq(Product::getIsShow, true)
        );
        return AjaxResult.success(products);
    }

    @Override
    public AjaxResult getShowProductDetail(Long id) {
        LambdaQueryWrapper<Product> selectWrapper = new LambdaQueryWrapper<>();
        selectWrapper
                .eq(Product::getId, id)
                .eq(Product::getIsShow, true);
        Product product = productMapper.selectOne(selectWrapper);
        return AjaxResult.success(product);
    }

}
