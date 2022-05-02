package com.example.cran.service.impl;

import com.example.cran.entity.Qiepianthroughput;
import com.example.cran.mapper.DelayMapper;
import com.example.cran.mapper.QiepianthroughputMapper;
import com.example.cran.service.IQiepianthroughputService;
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
public class QiepianthroughputServiceImpl extends ServiceImpl<QiepianthroughputMapper, Qiepianthroughput> implements IQiepianthroughputService {
    @Autowired
    private QiepianthroughputMapper qiepianthroughputMapper;

    @Override
    public boolean deleteAll() {
        return qiepianthroughputMapper.deleteAll();
    }
}
