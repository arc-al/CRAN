package com.example.cran.service.impl;

import com.example.cran.entity.Terminal;
import com.example.cran.mapper.TerminalMapper;
import com.example.cran.service.ITerminalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ---
 * @since 2022-04-18
 */
@Service
public class TerminalServiceImpl extends ServiceImpl<TerminalMapper, Terminal> implements ITerminalService {

    @Autowired
    private TerminalMapper terminalMapper;

    @Override
    public Integer getMaxid() {
        return terminalMapper.getMaxid();
    }

    @Override
    public boolean deleteAll() {
        return terminalMapper.deleteAll();
    }
}
