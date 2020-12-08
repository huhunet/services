package com.miracle.iam.service;

import com.miracle.iam.domain.entity.User;

public interface ITokenService {
    String generateToken(User user);
    String refreshToken(String token,String userName);
}
