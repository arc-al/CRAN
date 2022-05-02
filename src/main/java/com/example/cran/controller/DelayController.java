package com.example.cran.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;

import com.example.cran.service.IDelayService;
import com.example.cran.entity.Delay;

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
@RequestMapping("/delay")
public class DelayController {

    @Resource
    private IDelayService delayService;

    @PostMapping()
    public boolean save(@RequestBody Delay delay){
        //@RequestBody注解的作用是将接收到的JOSN格式数据转化成User对象
        //新增或者更新

        return delayService.save(delay);

    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return delayService.removeById(id);
    }

    @GetMapping
    public List<Delay> findAll() {
        return delayService.list();
    }

    @GetMapping("/{id}")
    public Delay findOne(@PathVariable Integer id) {
        return delayService.getById(id);
    }

    @GetMapping("/page")
    public IPage<Delay> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String parm) {
        IPage<Delay> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Delay> queryWrapper = new QueryWrapper<>();
        if(!"".equals(parm)){
        queryWrapper.like("parm",parm);
        }
        return delayService.page(page, queryWrapper);
    }
}
