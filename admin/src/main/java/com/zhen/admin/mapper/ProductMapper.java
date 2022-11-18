package com.zhen.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhen.admin.domain.Book;
import com.zhen.admin.domain.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
