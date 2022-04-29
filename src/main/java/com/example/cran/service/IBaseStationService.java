package com.example.cran.service;

import com.example.cran.entity.BaseStation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
    List<Object> counterCover(String longitude1, String longitude2, String latitude1, String latitude2);
    boolean saveData(BaseStation baseStation);
}
