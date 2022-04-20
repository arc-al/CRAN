package com.example.cran.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;

import com.example.cran.service.ISystemtableService;
import com.example.cran.entity.Systemtable;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xuliyan
 * @since 2022-04-11
 */
@Controller
@RequestMapping("//systemtable")
public class SystemtableController {

    @Resource
    private ISystemtableService systemtableService;

    @PostMapping()
    public boolean save(@RequestBody Systemtable systemtable){
        //@RequestBody注解的作用是将接收到的JOSN格式数据转化成User对象
        //新增或者更新
        return systemtableService.saveOrUpdate(systemtable);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return systemtableService.removeById(id);
    }

    @GetMapping
    public List<Systemtable> findAll() {
        return systemtableService.list();
    }

    @GetMapping("/{id}")
    public Systemtable findOne(@PathVariable Integer id) {
        return systemtableService.getById(id);
    }

    @GetMapping("/page")
    public Page<Systemtable> findPage(@RequestParam Integer pageNum,
    @RequestParam Integer pageSize) {
        return systemtableService.page(new Page<>(pageNum, pageSize));
    }
}
