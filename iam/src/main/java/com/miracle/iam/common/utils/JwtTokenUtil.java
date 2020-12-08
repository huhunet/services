package com.miracle.iam.common.utils;

import com.miracle.iam.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";

    private static final String CLAIM_KEY_ROLE = "role";

    private static final String SIGN_KEY = "SIGNKEY";

    /**
     * 5天(毫秒)
     */
    private static final long EXPIRATION_TIME = 5*60*1000;

    /**
     * 签发JWT
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_USERNAME, user.getUsername());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(Instant.now().toEpochMilli() + EXPIRATION_TIME) )
                .signWith(SignatureAlgorithm.HS512,SIGN_KEY)
                .compact();
    }

    /**
     * 验证JWT
     */
    public Boolean validateToken(String token, String userName) {
        String username = getUsernameFromToken(token);
        return (username.equals(userName) && !isTokenExpired(token));
    }

    /**
     * 获取token是否过期
     */
    private Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 根据token获取username
     */
    private String getUsernameFromToken(String token) {
        String userName = getClaimsFromToken(token).getSubject();
        return userName;
    }

    /**
     * 根据token获取username
     */
    public User getUserFromToken(String token) {
        String userName = this.getUsernameFromToken(token);
        return User.builder().userName(userName).build();
    }


    /**m
     * 获取token的过期时间
     */
    private Date getExpirationDateFromToken(String token) {
        Date expiration = getClaimsFromToken(token).getExpiration();
        return expiration;
    }

    /**
     * 解析JWT
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SIGN_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

}
