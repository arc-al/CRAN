package com.example.cran.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;

import com.example.cran.service.IQiepianthroughputService;
import com.example.cran.entity.Qiepianthroughput;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ---
 * @since 2022-05-02
 */
@RestController
@RequestMapping("/qiepianthroughput")
public class QiepianthroughputController {

    @Resource
    private IQiepianthroughputService qiepianthroughputService;

    @PostMapping()
    public boolean save(@RequestBody Qiepianthroughput qiepianthroughput){
        //@RequestBody注解的作用是将接收到的JOSN格式数据转化成User对象
        //新增或者更新

        return qiepianthroughputService.save(qiepianthroughput);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return qiepianthroughputService.removeById(id);
    }

    @GetMapping
    public List<Qiepianthroughput> findAll() {
        return qiepianthroughputService.list();
    }

    @GetMapping("/{id}")
    public Qiepianthroughput findOne(@PathVariable Integer id) {
        return qiepianthroughputService.getById(id);
    }

    @GetMapping("/page")
    public IPage<Qiepianthroughput> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String parm) {
        IPage<Qiepianthroughput> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Qiepianthroughput> queryWrapper = new QueryWrapper<>();
        if(!"".equals(parm)){
        queryWrapper.like("parm",parm);
        }
        return qiepianthroughputService.page(page, queryWrapper);
    }
}
