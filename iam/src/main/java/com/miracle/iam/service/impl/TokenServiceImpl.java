package com.miracle.iam.service.impl;

import com.miracle.iam.common.utils.JwtTokenUtil;
import com.miracle.iam.domain.entity.User;
import com.miracle.iam.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements ITokenService {
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public String generateToken(User user) {
        return null;
    }

    @Override
    public String refreshToken(String token,String userName) {
        boolean bValid = jwtTokenUtil.validateToken(token,userName);
        if(bValid){
            return jwtTokenUtil.generateToken(User.builder().userName(userName).build());
        }else{
            return null;
        }



    }
}
