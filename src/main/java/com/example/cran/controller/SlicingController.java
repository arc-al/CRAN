package com.example.cran.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;

import com.example.cran.service.ISlicingService;
import com.example.cran.entity.Slicing;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ---
 * @since 2022-04-18
 */
@RestController
@RequestMapping("/slicing")
public class SlicingController {

    @Resource
    private ISlicingService slicingService;

    @PostMapping()
    public boolean save(@RequestBody Slicing slicing){
        //@RequestBody注解的作用是将接收到的JOSN格式数据转化成User对象
        //新增或者更新
        if(slicing.getId()==null){
            slicing.setId(slicingService.getMaxid()+1);
            return slicingService.save(slicing);
        } else {
            return slicingService.updateById(slicing);
        }
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return slicingService.removeById(id);
    }

    @GetMapping
    public List<Slicing> findAll() {
        return slicingService.list();
    }

    @GetMapping("/{id}")
    public Slicing findOne(@PathVariable Integer id) {
        return slicingService.getById(id);
    }

    @GetMapping("/page")
    public IPage<Slicing> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String id,
                                   @RequestParam(defaultValue = "") String type,
                                   @RequestParam(defaultValue = "") String service) {
        IPage<Slicing> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Slicing> queryWrapper = new QueryWrapper<>();
        if(!"".equals(id)){
        queryWrapper.eq("id",id);
        }
        if(!"".equals(type)){
            queryWrapper.like("type",type);
        }
        if(!"".equals(service)){
            queryWrapper.like("service",service);
        }
        return slicingService.page(page, queryWrapper);
    }

    @PostMapping("/bindSlicing")
    public Boolean bind(@RequestParam(defaultValue = "") String businessName,
                        @RequestParam(defaultValue = "") String slicingId){
        return slicingService.bind(businessName,slicingId);
    }

}
