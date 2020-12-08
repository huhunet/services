package com.miracle.iam.repository.dao;

import com.miracle.iam.domain.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByUserName(String userName);

    List<User> selectByAll();

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}