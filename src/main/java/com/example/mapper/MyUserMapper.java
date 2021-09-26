package com.example.mapper;

import com.example.bean.MyUserBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @description: TODO
 * @author: Falcone
 * @date: 2021/9/26 14:48
 */

@Mapper
@Repository
public interface MyUserMapper {
    @Select("select * from user where username = #{username}")
    MyUserBean selectByUsername(String username);
}
