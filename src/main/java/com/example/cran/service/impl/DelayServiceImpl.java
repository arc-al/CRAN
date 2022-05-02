package com.example.cran.service.impl;

import com.example.cran.entity.Delay;
import com.example.cran.mapper.DelayMapper;
import com.example.cran.service.IDelayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ---
 * @since 2022-05-02
 */
@Service
public class DelayServiceImpl extends ServiceImpl<DelayMapper, Delay> implements IDelayService {
    @Autowired
    private DelayMapper delayMapper;

    @Override
    public boolean deleteAll() {
        return delayMapper.deleteAll();
    }
}
