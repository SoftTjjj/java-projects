package com.example.cachebackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cachebackend.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
