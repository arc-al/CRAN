package com.example.cran.service.impl;

import com.example.cran.entity.Totalthroughput;
import com.example.cran.mapper.QiepianthroughputMapper;
import com.example.cran.mapper.TotalthroughputMapper;
import com.example.cran.service.ITotalthroughputService;
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
public class TotalthroughputServiceImpl extends ServiceImpl<TotalthroughputMapper, Totalthroughput> implements ITotalthroughputService {
    @Autowired
    private TotalthroughputMapper totalthroughputMapper;

    @Override
    public boolean deleteAll() {
        return totalthroughputMapper.deleteAll();
    }
}
