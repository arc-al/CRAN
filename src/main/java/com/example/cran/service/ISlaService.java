package com.example.cran.service;

import com.example.cran.entity.MatchDemand;
import com.example.cran.entity.Sla;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cran.entity.Slicing;
import com.example.cran.entity.TypicalBusiness;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ---
 * @since 2022-04-19
 */
public interface ISlaService extends IService<Sla> {
    List<Object> selectSla(MatchDemand matchDemand) throws IllegalAccessException;


}
