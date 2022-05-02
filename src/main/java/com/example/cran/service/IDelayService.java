package com.example.cran.service;

import com.example.cran.entity.Delay;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ---
 * @since 2022-05-02
 */
public interface IDelayService extends IService<Delay> {
    boolean deleteAll();
}
