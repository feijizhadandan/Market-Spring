package com.zhen.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhen.admin.domain.BuyRecord;
import com.zhen.admin.mapper.BuyRecordMapper;
import com.zhen.admin.service.BuyRecordService;
import com.zhen.common.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class BuyRecordServiceImpl extends ServiceImpl<BuyRecordMapper, BuyRecord> implements BuyRecordService {

    @Autowired
    private BuyRecordMapper brMapper;

    @Override
    public AjaxResult getBuyRecord(HttpServletRequest request) {
        List<BuyRecord> buyRecords = brMapper.selectList(null);
        return AjaxResult.success(buyRecords);
    }
}
