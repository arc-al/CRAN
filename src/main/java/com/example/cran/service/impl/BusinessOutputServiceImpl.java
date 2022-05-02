package com.example.cran.service.impl;

import com.example.cran.entity.BusinessOutput;
import com.example.cran.mapper.BusinessOutputMapper;
import com.example.cran.mapper.DelayMapper;
import com.example.cran.service.IBusinessOutputService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ---
 * @since 2022-05-02
 */
@Service
public class BusinessOutputServiceImpl extends ServiceImpl<BusinessOutputMapper, BusinessOutput> implements IBusinessOutputService {
    @Autowired
    private BusinessOutputMapper businessOutputMapper;

    @Override
    public boolean deleteAll() {
        return businessOutputMapper.deleteAll();
    }
}
