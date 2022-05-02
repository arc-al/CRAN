package com.example.cran.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;

import com.example.cran.service.IBusinessOutputService;
import com.example.cran.entity.BusinessOutput;

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
@RequestMapping("/business-output")
public class BusinessOutputController {

    @Resource
    private IBusinessOutputService businessOutputService;

    @PostMapping()
    public boolean save(@RequestBody BusinessOutput businessOutput){
        //@RequestBody注解的作用是将接收到的JOSN格式数据转化成User对象
        //新增或者更新
        if(businessOutput.getId()==null){
        return businessOutputService.save(businessOutput);
        } else {
        return businessOutputService.updateById(businessOutput);
        }
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return businessOutputService.removeById(id);
    }

    @GetMapping
    public List<BusinessOutput> findAll() {
        return businessOutputService.list();
    }

    @GetMapping("/{id}")
    public BusinessOutput findOne(@PathVariable Integer id) {
        return businessOutputService.getById(id);
    }

    @GetMapping("/page")
    public IPage<BusinessOutput> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String parm) {
        IPage<BusinessOutput> page = new Page<>(pageNum, pageSize);
        QueryWrapper<BusinessOutput> queryWrapper = new QueryWrapper<>();
        if(!"".equals(parm)){
        queryWrapper.like("parm",parm);
        }
        return businessOutputService.page(page, queryWrapper);
    }
}
