package com.example.cran.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cran.entity.User;
import com.example.cran.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper,User> {


    public boolean saveUser(User user) {
        if(user.getId()==null){
            return save(user);
        } else {
            return updateById(user);
        }
    }
//    @Autowired
//    private UserMapper UserMapper;
//
//    public int save(User user){
//        //没有id说明是新增数据
//        if(user.getId()==null){
//            return UserMapper.Insert(user);
//        }else{
//            return UserMapper.Update(user);
//        }
//    }
}
