package com.example.cran.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cran.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//@Mapper
public interface UserMapper extends BaseMapper<User> {
//    @Select("SELECT * FROM SYSTEMTABLE")
//    List<User> findAll();
//
//    @Insert("INSERT INTO SYSTEMTABLE(username,password,nickname,email,phone,address) VALUES(#{username}," +
//            "#{password},#{nickname},#{email},#{phone},#{address})")
//    int Insert(User user);
//
//    int Update(User user);
//
//    @Delete("DELETE FROM SYSTEMTABLE WHERE id = #{id}")
//    Integer deleteById(@Param("id") Integer id);
//
//    @Select("SELECT * FROM SYSTEMTABLE where username like concat('%',#{userName},'%') LIMIT #{pageNum},#{pageSize}")
//    List<User> selectPage(@Param("pageNum")Integer pageNum,
//                          @Param("pageSize")Integer pageSize,
//                          @Param("userName")String userName);
//
//    @Select("SELECT COUNT(*) FROM SYSTEMTABLE where username like concat('%',#{username},'%')")
//    int selectTotal(String userName);
}
