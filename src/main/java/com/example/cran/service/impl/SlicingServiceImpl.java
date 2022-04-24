package com.example.cran.service.impl;

import com.example.cran.entity.Slicing;
import com.example.cran.mapper.SlicingMapper;
import com.example.cran.service.ISlicingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ---
 * @since 2022-04-18
 */
@Service
public class SlicingServiceImpl extends ServiceImpl<SlicingMapper, Slicing> implements ISlicingService {

    @Autowired
    private SlicingMapper slicingMapper;


    @Override
    public int getMaxid() {
        return slicingMapper.getMaxid();
    }
    @Override
    public Boolean bind(String businessName, String slicingId) {
        return slicingMapper.bind(businessName,slicingId);
    }

}
