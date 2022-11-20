package com.zhen.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhen.admin.domain.BuyRecord;
import com.zhen.admin.domain.CartRecord;
import com.zhen.admin.dto.CartDto;
import com.zhen.admin.dto.PayProductDto;
import com.zhen.admin.mapper.BuyRecordMapper;
import com.zhen.admin.mapper.CartRecordMapper;
import com.zhen.admin.service.CartRecordService;
import com.zhen.admin.vo.CartVo;
import com.zhen.common.domain.AjaxResult;
import com.zhen.framework.security.service.impl.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartRecordServiceImpl extends ServiceImpl<CartRecordMapper, CartRecord> implements CartRecordService {

    @Autowired
    private CartRecordMapper cartRecordMapper;

    @Autowired
    private BuyRecordMapper buyRecordMapper;

    @Autowired
    private TokenService tokenService;

    @Override
    public AjaxResult getBuyerCart(HttpServletRequest request) {
        Long buyer_id = tokenService.getLoginUserDetail(request).getId();
        List<CartVo> allRecord = cartRecordMapper.getAllRecord(buyer_id);
        for (CartVo cartVo : allRecord) {
            cartVo.setTotalPrice(cartVo.getCount() * cartVo.getProductPrice());
        }
        return AjaxResult.success(allRecord);
    }

    @Override
    public AjaxResult addCartRecord(CartDto addCartDto, HttpServletRequest request) {
        Long buyerId = tokenService.getLoginUserDetail(request).getId();
        Long productId = addCartDto.getProductId();
        // 先查看购物车中有无该商品的记录
        CartRecord cartRecord = cartRecordMapper.selectOne(
                new LambdaQueryWrapper<CartRecord>()
                .eq(CartRecord::getBuyerId, buyerId)
                .eq(CartRecord::getProductId, productId)
        );
        // 购物车中没有该商品
        if (cartRecord == null)
            cartRecordMapper.insert(new CartRecord(buyerId, productId, addCartDto.getCount()));
        // 购物车中有该商品，则叠加数量
        else {
            cartRecordMapper.update(null,
                    new LambdaUpdateWrapper<CartRecord>()
                            .eq(CartRecord::getBuyerId, buyerId)
                            .eq(CartRecord::getProductId, productId)
                            .set(CartRecord::getCount, cartRecord.getCount() + addCartDto.getCount()));
        }
        return AjaxResult.success("添加购物车成功");
    }

    @Override
    public AjaxResult changeCartRecord(CartDto changeCartDto, HttpServletRequest request) {
        Long buyerId = tokenService.getLoginUserDetail(request).getId();
        Long productId = changeCartDto.getProductId();
        cartRecordMapper.update(null,
                new LambdaUpdateWrapper<CartRecord>()
                        .eq(CartRecord::getBuyerId, buyerId)
                        .eq(CartRecord::getProductId, productId)
                        .set(CartRecord::getCount, changeCartDto.getCount()));
        return AjaxResult.success("修改购物车数量成功");
    }

    @Override
    public AjaxResult deleteCartRecord(Long productId, HttpServletRequest request) {
        Long buyerId = tokenService.getLoginUserDetail(request).getId();
        int delete = cartRecordMapper.delete(
                new LambdaQueryWrapper<CartRecord>()
                        .eq(CartRecord::getBuyerId, buyerId)
                        .eq(CartRecord::getProductId, productId)
        );
        if (delete == 0)
            return AjaxResult.error("删除失败");
        else
            return AjaxResult.success("删除成功");
    }

    @Override
    public AjaxResult payProduct(List<PayProductDto> payProductList, HttpServletRequest request) {
        Long buyerId = tokenService.getLoginUserDetail(request).getId();
        for (PayProductDto payProductDto : payProductList) {
            // 添加用户购买记录
            buyRecordMapper.insert(new BuyRecord(buyerId, payProductDto.getId(), payProductDto.getCount(), LocalDateTime.now()));
            // 清空对应的购物车信息
            deleteCartRecord(payProductDto.getId(), request);
        }
        return AjaxResult.success("购买成功");
    }
}
