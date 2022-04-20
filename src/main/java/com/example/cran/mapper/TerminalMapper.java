package com.example.cran.mapper;

import com.example.cran.entity.Terminal;
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
public interface TerminalMapper extends BaseMapper<Terminal> {

    @Select("select max(id) from terminal")
    int getMaxid();
}
