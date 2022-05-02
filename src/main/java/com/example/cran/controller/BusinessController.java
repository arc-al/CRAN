package com.example.cran.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;

import com.example.cran.service.IBusinessService;
import com.example.cran.entity.Business;

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
@RequestMapping("/business")
public class BusinessController {

    @Resource
    private IBusinessService businessService;

    @DeleteMapping
    public Boolean deleteAll(){
        return businessService.deleteAll();
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return businessService.removeById(id);
    }

    @GetMapping
    public List<Business> findAll() {
        return businessService.list();
    }

    @GetMapping("/{id}")
    public Business findOne(@PathVariable Integer id) {
        return businessService.getById(id);
    }

    @GetMapping("/page")
    public IPage<Business> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String parm) {
        IPage<Business> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Business> queryWrapper = new QueryWrapper<>();
        if(!"".equals(parm)){
        queryWrapper.like("parm",parm);
        }
        return businessService.page(page, queryWrapper);
    }
}
