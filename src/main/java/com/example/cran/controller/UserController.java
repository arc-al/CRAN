package com.example.cran.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.cran.entity.User;

import com.example.cran.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/User")
public class UserController {

//    @Autowired
//    private UserMapper UserMapper;

    @Autowired
    private UserService UserService;

    @PostMapping()
    public boolean save(@RequestBody User user){
        //@RequestBody注解的作用是将接收到的JOSN格式数据转化成User对象
        //新增或者更新
        return UserService.saveUser(user);
    }

    @GetMapping()
    public List<User> findAll(){
        //查询所有数据

        return UserService.list();
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return UserService.removeById(id);
    }

    @PostMapping("/deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return UserService.removeByIds(ids);
    }

    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum ,
                                @RequestParam Integer pageSize ,
                                @RequestParam(defaultValue = "") String userName,
                                @RequestParam(defaultValue = "") String nickName,
                                @RequestParam(defaultValue = "") String email,
                                @RequestParam(defaultValue = "") String address){
        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(!"".equals(userName)){
            queryWrapper.like("username",userName);
        }
        if(!"".equals(nickName)){
            queryWrapper.like("nickname",nickName);
        }
        if(!"".equals(email)){
            queryWrapper.like("email",email);
        }
        if(!"".equals(address)){
            queryWrapper.like("address",address);
        }
        queryWrapper.orderByAsc("id");
        return UserService.page(page, queryWrapper);
    }

}
