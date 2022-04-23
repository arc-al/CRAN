package com.example.cran.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;

import com.example.cran.service.IBaseStationService;
import com.example.cran.entity.BaseStation;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ---
 * @since 2022-04-17
 */
@RestController
@RequestMapping("/base-station")
public class BaseStationController {

    @Resource
    private IBaseStationService baseStationService;

    @PostMapping()
    public boolean save(@RequestBody BaseStation baseStation){
        //@RequestBody注解的作用是将接收到的JOSN格式数据转化成User对象
        //新增或者更新
        if(baseStation.getId()==null){
            baseStation.setId(baseStationService.getMaxid()+1);
            return baseStationService.save(baseStation);
        } else {
            return baseStationService.updateById(baseStation);
        }
    }


    @PostMapping("/deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return baseStationService.removeByIds(ids);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return baseStationService.removeById(id);
    }

    @GetMapping
    public List<BaseStation> findAll() {
        return baseStationService.list();
    }

    @GetMapping("/{id}")
    public BaseStation findOne(@PathVariable Integer id) {
        return baseStationService.getById(id);
    }

    @GetMapping("/page")
    public IPage<BaseStation> findPage(@RequestParam Integer pageNum,
                                      @RequestParam Integer pageSize,
                                      @RequestParam(defaultValue = "") String id,
                                       @RequestParam(defaultValue = "") String type,
                                      @RequestParam(defaultValue = "") String area,
                                      @RequestParam(defaultValue = "") String longitude,
                                      @RequestParam(defaultValue = "") String latitude) {
        IPage<BaseStation> page = new Page<>(pageNum, pageSize);
        QueryWrapper<BaseStation> oqw = new QueryWrapper<>();
        if(!"".equals(id)){
            oqw.eq("id",id);
        }
        if(!"".equals(type)){
            oqw.like("type",type);
        }
        if(!"".equals(area)){
            oqw.like("area",area);
        }
        if(!"".equals(longitude)){
            oqw.like("longitude",longitude);
        }
        if(!"".equals(latitude)){
            oqw.like("latitude",latitude);
        }
        oqw.orderByAsc("id");
        return baseStationService.page(page, oqw);
    }
}
