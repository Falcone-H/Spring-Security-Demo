package com.example.mapper;

import com.example.bean.MyResourceBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: TODO
 * @author: Falcone
 * @date: 2021/9/26 18:54
 */

@Repository
@Mapper
public interface ResourceMapper {
    // 从数据库中查询所有资源
    @Select("select * from resource")
    List<MyResourceBean> selectAllResource();
}
