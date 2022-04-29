package com.example.cran.service.impl;

import com.example.cran.entity.Terminal;
import com.example.cran.mapper.TerminalMapper;
import com.example.cran.service.ITerminalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cran.utils.CoordinateTrans;
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

    @Autowired
    private ITerminalService terminalService;

    @Override
    public Integer getMaxid() {
        if(terminalService.list().size()==0){
            return 1;
        } else {
            return terminalMapper.getMaxid();
        }
    }

    @Override
    public boolean deleteAll() {
        return terminalMapper.deleteAll();
    }

    @Override
    public boolean saveData(Terminal terminal) {
        terminal.setX(CoordinateTrans.longitudeToX(terminal.getLongitude()));
        terminal.setY(CoordinateTrans.latitudeToY(terminal.getLatitude()));
        if(terminal.getId()==null){
            terminal.setId(terminalService.getMaxid()+1);
            return terminalService.save(terminal);
        } else {
            return terminalService.updateById(terminal);
        }
    }
}
