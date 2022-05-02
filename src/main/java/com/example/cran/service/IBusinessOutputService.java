package com.example.cran.service;

import com.example.cran.entity.BusinessOutput;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ---
 * @since 2022-05-02
 */
public interface IBusinessOutputService extends IService<BusinessOutput> {
    boolean deleteAll();
}
