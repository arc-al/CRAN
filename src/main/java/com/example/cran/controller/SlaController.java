package com.example.cran.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.cran.entity.MatchDemand;
import com.example.cran.entity.Slicing;
import com.example.cran.entity.TypicalBusiness;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;

import com.example.cran.service.ISlaService;
import com.example.cran.entity.Sla;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ---
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/sla")
public class SlaController {

    @Resource
    private ISlaService slaService;

    @PostMapping()
    public boolean save(@RequestBody Sla sla){
        //@RequestBody注解的作用是将接收到的JOSN格式数据转化成User对象
        //新增或者更新
        if(sla.getId()==null){
        return slaService.save(sla);
        } else {
        return slaService.updateById(sla);
        }
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return slaService.removeById(id);
    }

    @GetMapping
    public List<Sla> findAll() {
        return slaService.list();
    }

    @GetMapping("/{id}")
    public Sla findOne(@PathVariable Integer id) {
        return slaService.getById(id);
    }

    @GetMapping("/page")
    public IPage<Sla> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String id,
                               @RequestParam(defaultValue = "") String type) {
        IPage<Sla> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Sla> queryWrapper = new QueryWrapper<>();
        if(!"".equals(id)){
            queryWrapper.eq("id",id);
        }
        if(!"".equals(type)){
            queryWrapper.like("type",type);
        }
        return slaService.page(page, queryWrapper);
    }

    @PostMapping("/selectSla")
    public List<Object> selectSla(@RequestBody MatchDemand matchDemand) throws IllegalAccessException {
        return slaService.selectSla(matchDemand);
    }



}
