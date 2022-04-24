package com.example.cran.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;

import com.example.cran.service.ITypicalBusinessService;
import com.example.cran.entity.TypicalBusiness;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ---
 * @since 2022-04-23
 */
@RestController
@RequestMapping("/typical-business")
public class TypicalBusinessController {

    @Resource
    private ITypicalBusinessService typicalBusinessService;

    @PostMapping()
    public boolean save(@RequestBody TypicalBusiness typicalBusiness){
        //@RequestBody注解的作用是将接收到的JOSN格式数据转化成User对象
        //新增或者更新
        if(typicalBusiness.getId()==null){
        return typicalBusinessService.save(typicalBusiness);
        } else {
        return typicalBusinessService.updateById(typicalBusiness);
        }
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return typicalBusinessService.removeById(id);
    }

    @GetMapping
    public List<TypicalBusiness> findAll() {
        return typicalBusinessService.list();
    }

    @GetMapping("/{id}")
    public TypicalBusiness findOne(@PathVariable Integer id) {
        return typicalBusinessService.getById(id);
    }

//    @GetMapping("/page")
//    public IPage<TypicalBusiness> findPage(@RequestParam Integer pageNum,
//                                    @RequestParam Integer pageSize,
//                                    @RequestParam(defaultValue = "") String parm) {
//        IPage<TypicalBusiness> page = new Page<>(pageNum, pageSize);
//        QueryWrapper<TypicalBusiness> queryWrapper = new QueryWrapper<>();
//        if(!"".equals(parm)){
//        queryWrapper.like("parm",parm);
//        }
//        return typicalBusinessService.page(page, queryWrapper);
//    }
}
