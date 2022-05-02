package com.example.cran.mapper;

import com.example.cran.entity.Totalthroughput;
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
public interface TotalthroughputMapper extends BaseMapper<Totalthroughput> {
    @Delete("delete from totalthroughput where 1=1")
    boolean deleteAll();
}
