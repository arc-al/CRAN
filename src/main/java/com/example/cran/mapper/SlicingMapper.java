package com.example.cran.mapper;

import com.example.cran.entity.Slicing;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ---
 * @since 2022-04-18
 */
public interface SlicingMapper extends BaseMapper<Slicing> {

    @Select("select max(id) from slicing")
    int getMaxid();
}
