package com.example.cran.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.cran.service.IConnectionUpdateService;
import com.example.cran.utils.CoordinateTrans;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IConnectionUpdateService connectionUpdateService;

    @Resource
    private IBaseStationService baseStationService;

    @PostMapping()
    public boolean save(@RequestBody BaseStation baseStation){
        //@RequestBody注解的作用是将接收到的JOSN格式数据转化成User对象
        //新增或者更新
        return baseStationService.saveData(baseStation);
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
        connectionUpdateService.bs_terminalUpdate();
        connectionUpdateService.bs_mecUpdate();
        return baseStationService.list();
    }

    @GetMapping("/{id}")
    public BaseStation findOne(@PathVariable Integer id) {
        connectionUpdateService.bs_terminalUpdate();
        connectionUpdateService.bs_mecUpdate();
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
        connectionUpdateService.bs_terminalUpdate();
        connectionUpdateService.bs_mecUpdate();
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

    //覆盖
    @GetMapping("/cover")
    public List<Object> counterCover(@RequestParam String longitude1, @RequestParam String longitude2, @RequestParam String latitude1, @RequestParam String latitude2) {
        return baseStationService.counterCover(longitude1,longitude2,latitude1,latitude2);
    }
}
