package com.example.cran.mapper;

import com.example.cran.entity.Business;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ---
 * @since 2022-05-02
 */
public interface BusinessMapper extends BaseMapper<Business> {

    @Delete("delete from business where 1=1")
    boolean deleteAll();
}
