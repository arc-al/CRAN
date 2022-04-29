package com.example.cran.service.impl;

import com.example.cran.entity.Slicing;
import com.example.cran.entity.Terminal;
import com.example.cran.mapper.SlicingMapper;
import com.example.cran.service.ISlicingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cran.service.ITerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ---
 * @since 2022-04-18
 */
@Service
public class SlicingServiceImpl extends ServiceImpl<SlicingMapper, Slicing> implements ISlicingService {

    @Autowired
    private SlicingMapper slicingMapper;

    @Autowired
    private ITerminalService terminalService;


    @Override
    public int getMaxid() {
        return slicingMapper.getMaxid();
    }

    /**
     * 更新切片表中”service“字段为businessName参数，更新终端表中字段”business_type“字段为businessName的终端的字段”conn_slicing“为slicingId
     * @param businessName
     * @param slicingId
     * @return
     */
    @Override
    public Boolean bind(String businessName, String slicingId) {
        //更新终端表中字段”business_type“字段为businessName的终端的字段”conn_slicing“为slicingId
        List<Terminal> terminalList = terminalService.list();
        for(Terminal tm: terminalList){
            if(tm.getBusinessType().equals(businessName)){
                tm.setConnSlicing(Integer.valueOf(slicingId));
                terminalService.updateById(tm);
            }
        }


        Boolean bindFlag = slicingMapper.bind(businessName, slicingId);
        return bindFlag;
    }

}
