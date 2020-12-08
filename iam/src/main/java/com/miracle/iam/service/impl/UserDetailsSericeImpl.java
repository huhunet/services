package com.miracle.iam.service.impl;

import com.miracle.iam.domain.entity.User;
import com.miracle.iam.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserDetailsSericeImpl implements UserDetailsService {
    @Autowired
    IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("login -- userName：{}", username);
        Optional<User> user = Optional.ofNullable(userService.getUserByName(username));
        if(!user.isPresent()){
            throw new UsernameNotFoundException("UserName is invalid");
        }
        log.info(user.get().toString());
        return user.get();
    }
}
