package com.example.cran.service.impl;

import com.example.cran.entity.Business;
import com.example.cran.mapper.BusinessMapper;
import com.example.cran.service.IBusinessService;
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
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements IBusinessService {

    @Autowired
    private BusinessMapper businessMapper;

    @Override
    public boolean deleteAll() {
        return businessMapper.deleteAll();
    }
}
