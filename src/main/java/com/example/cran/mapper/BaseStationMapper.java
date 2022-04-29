package com.example.cran.mapper;

import com.example.cran.entity.BaseStation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ---
 * @since 2022-04-17
 */
public interface BaseStationMapper extends BaseMapper<BaseStation> {

    @Select("select max(id) from base_station")
    int getMaxid();


    @Delete("delete from base_station where 1=1")
    boolean deleteAll();
}
