package com.example.cran.service;

import com.example.cran.entity.BaseStation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ---
 * @since 2022-04-17
 */
public interface IBaseStationService extends IService<BaseStation> {

    int getMaxid();
    boolean deleteAll();
}
