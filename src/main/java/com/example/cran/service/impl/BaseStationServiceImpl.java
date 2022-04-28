package com.example.cran.service.impl;

import com.example.cran.entity.BaseStation;
import com.example.cran.mapper.BaseStationMapper;
import com.example.cran.service.IBaseStationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ---
 * @since 2022-04-17
 */
@Service
public class BaseStationServiceImpl extends ServiceImpl<BaseStationMapper, BaseStation> implements IBaseStationService {


    @Autowired
    private BaseStationMapper baseStationMapper;

    @Override
    public int getMaxid() {
        return baseStationMapper.getMaxid();
    }

    @Override
    public boolean deleteAll() {
        return baseStationMapper.deleteAll();
    }
}
