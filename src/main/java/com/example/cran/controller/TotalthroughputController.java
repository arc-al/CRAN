package com.example.cran.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;

import com.example.cran.service.ITotalthroughputService;
import com.example.cran.entity.Totalthroughput;

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
@RequestMapping("/totalthroughput")
public class TotalthroughputController {

    @Resource
    private ITotalthroughputService totalthroughputService;

    @PostMapping()
    public boolean save(@RequestBody Totalthroughput totalthroughput){
        //@RequestBody注解的作用是将接收到的JOSN格式数据转化成User对象
        //新增或者更新

        return totalthroughputService.save(totalthroughput);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return totalthroughputService.removeById(id);
    }

    @GetMapping
    public List<Totalthroughput> findAll() {
        return totalthroughputService.list();
    }

    @GetMapping("/{id}")
    public Totalthroughput findOne(@PathVariable Integer id) {
        return totalthroughputService.getById(id);
    }

    @GetMapping("/page")
    public IPage<Totalthroughput> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String parm) {
        IPage<Totalthroughput> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Totalthroughput> queryWrapper = new QueryWrapper<>();
        if(!"".equals(parm)){
        queryWrapper.like("parm",parm);
        }
        return totalthroughputService.page(page, queryWrapper);
    }
}
