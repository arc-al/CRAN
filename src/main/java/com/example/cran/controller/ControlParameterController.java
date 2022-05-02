package com.example.cran.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;

import com.example.cran.service.IControlParameterService;
import com.example.cran.entity.ControlParameter;

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
@RequestMapping("/control-parameter")
public class ControlParameterController {

    @Resource
    private IControlParameterService controlParameterService;

    @PostMapping()
    public boolean save(@RequestBody ControlParameter controlParameter){
        //@RequestBody注解的作用是将接收到的JOSN格式数据转化成User对象
        //新增或者更新
        if(controlParameter.getId()==null){
        return controlParameterService.save(controlParameter);
        } else {
        return controlParameterService.updateById(controlParameter);
        }
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return controlParameterService.removeById(id);
    }

    @GetMapping
    public List<ControlParameter> findAll() {
        return controlParameterService.list();
    }

    @GetMapping("/{id}")
    public ControlParameter findOne(@PathVariable Integer id) {
        return controlParameterService.getById(id);
    }

    @GetMapping("/page")
    public IPage<ControlParameter> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String parm) {
        IPage<ControlParameter> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ControlParameter> queryWrapper = new QueryWrapper<>();
        if(!"".equals(parm)){
        queryWrapper.like("parm",parm);
        }
        return controlParameterService.page(page, queryWrapper);
    }
}
