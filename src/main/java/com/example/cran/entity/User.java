package com.example.cran.entity;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@TableName(value = "systemtable")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private String nickname;
    private String phone;
    private String address;
}
