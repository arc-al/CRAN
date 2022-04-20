package com.example.cran.service;

import com.example.cran.entity.Terminal;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ---
 * @since 2022-04-18
 */
public interface ITerminalService extends IService<Terminal> {

    Integer getMaxid();
}
