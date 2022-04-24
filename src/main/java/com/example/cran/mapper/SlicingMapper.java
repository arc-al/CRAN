package com.example.cran.mapper;

import com.example.cran.entity.Slicing;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Update("update slicing set service=#{businessName} where id=#{slicingId}")
    Boolean bind(String businessName, String slicingId);
}
