package com.example.cran.service;

import com.example.cran.entity.Totalthroughput;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ---
 * @since 2022-05-02
 */
public interface ITotalthroughputService extends IService<Totalthroughput> {
    boolean deleteAll();
}
