package com.example.cran.mapper;

import com.example.cran.entity.Mec;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ---
 * @since 2022-04-18
 */
public interface MecMapper extends BaseMapper<Mec> {

    @Select("select max(id) from mec")
    int getMaxid();

    @Delete("delete from mec where 1=1")
    boolean deleteAll();
}
