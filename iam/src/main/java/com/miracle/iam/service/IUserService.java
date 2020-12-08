package com.miracle.iam.service;

import com.miracle.iam.domain.entity.User;

import java.util.List;

public interface IUserService {

    /**
     *
     * @param userName
     * @return
     */
    User getUserByName(String userName);

    List<User> list();

    /**
     *
     * @param user
     * @return
     */
    boolean addUser(User user);

}
