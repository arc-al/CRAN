package com.example.cran.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;

import com.example.cran.service.ITerminalService;
import com.example.cran.entity.Terminal;

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
@RequestMapping("/terminal")
public class TerminalController {

    @Resource
    private ITerminalService terminalService;

    @PostMapping()
    public boolean save(@RequestBody Terminal terminal){
        //@RequestBody注解的作用是将接收到的JOSN格式数据转化成User对象
        //新增或者更新
        if(terminal.getId()==null){
            terminal.setId(terminalService.getMaxid()+1);
            return terminalService.save(terminal);
        } else {
            return terminalService.updateById(terminal);
        }
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return terminalService.removeById(id);
    }

    @GetMapping
    public List<Terminal> findAll() {
        return terminalService.list();
    }

    @GetMapping("/{id}")
    public Terminal findOne(@PathVariable Integer id) {
        return terminalService.getById(id);
    }

    @GetMapping("/page")
    public IPage<Terminal> findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String id,
                                   @RequestParam(defaultValue = "") String business_type  ,
                                   @RequestParam(defaultValue = "") String longitude,
                                   @RequestParam(defaultValue = "") String latitude,
                                   @RequestParam(defaultValue = "") String conn_slicing,
                                   @RequestParam(defaultValue = "") String conn_bs) {

        IPage<Terminal> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Terminal> queryWrapper = new QueryWrapper<>();
        if(!"".equals(id)){
            queryWrapper.eq("id",id);
        }
        if(!"".equals(business_type)){
            queryWrapper.like("business_type",business_type);
        }
        if(!"".equals(longitude)){
            queryWrapper.like("longitude",longitude);
        }
        if(!"".equals(latitude)){
            queryWrapper.like("latitude",latitude);
        }
        if(!"".equals(conn_slicing)){
            queryWrapper.like("conn_slicing",conn_slicing);
        }
        if(!"".equals(conn_bs)){
            queryWrapper.like("conn_bs",conn_bs);
        }
        queryWrapper.orderByAsc("id");
        return terminalService.page(page, queryWrapper);
    }
}
