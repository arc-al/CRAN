package com.example.cran.service;

import com.example.cran.entity.Mec;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ---
 * @since 2022-04-18
 */
public interface IMecService extends IService<Mec> {

    Integer getMaxid();
}
