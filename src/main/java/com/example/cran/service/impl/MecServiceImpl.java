package com.example.cran.service.impl;

import com.example.cran.entity.Mec;
import com.example.cran.mapper.MecMapper;
import com.example.cran.service.IMecService;
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
public class MecServiceImpl extends ServiceImpl<MecMapper, Mec> implements IMecService {

    @Autowired
    private MecMapper mecMapper;

    @Autowired
    private IMecService mecService;

    @Override
    public Integer getMaxid() {
        if(mecService.list().size()==0){
            return 1;
        } else {
            return mecMapper.getMaxid();
        }
    }

    @Override
    public boolean deleteAll() {
        return mecMapper.deleteAll();
    }
}
