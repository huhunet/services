package com.miracle.iam.service.impl;

import com.miracle.iam.domain.entity.User;
import com.miracle.iam.repository.dao.UserMapper;
import com.miracle.iam.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"userCache"})
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Cacheable("user")
    public User getUserByName(String userName) {
        return userMapper.selectByUserName(userName);
    }

    @Override
    public List<User> list() {
        return userMapper.selectByAll();
    }

    @Override
    public boolean addUser(User user) {
        if(null!)
    }


}
