package com.example.cran.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;

import com.example.cran.service.IMecService;
import com.example.cran.entity.Mec;

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
@RequestMapping("/mec")
public class MecController {

    @Resource
    private IMecService mecService;

    @PostMapping()
    public boolean save(@RequestBody Mec mec){
        //@RequestBody注解的作用是将接收到的JOSN格式数据转化成User对象
        //新增或者更新
        if(mec.getId()==null){
            mec.setId(mecService.getMaxid()+1);
            return mecService.save(mec);
        } else {
            return mecService.updateById(mec);
        }
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return mecService.removeById(id);
    }

    @GetMapping
    public List<Mec> findAll() {
        return mecService.list();
    }

    @GetMapping("/{id}")
    public Mec findOne(@PathVariable Integer id) {
        return mecService.getById(id);
    }

    @GetMapping("/page")
    public IPage<Mec> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String id,
                               @RequestParam(defaultValue = "") String area,
                               @RequestParam(defaultValue = "") String longitude,
                               @RequestParam(defaultValue = "") String latitude,
                               @RequestParam(defaultValue = "") String connSlicing) {
        IPage<Mec> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Mec> queryWrapper = new QueryWrapper<>();
        if(!"".equals(id)){
        queryWrapper.eq("id",id);
        }
        if(!"".equals(area)){
            queryWrapper.like("area",area);
        }
        if(!"".equals(longitude)){
            queryWrapper.like("longitude",id);
        }
        if(!"".equals(latitude)){
            queryWrapper.like("latitude",id);
        }
        if(!"".equals(connSlicing)){
            queryWrapper.like("connSlicing",id);
        }
        return mecService.page(page, queryWrapper);
    }
}
