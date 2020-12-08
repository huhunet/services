package com.miracle.iam.web;

import com.miracle.iam.domain.entity.User;
import com.miracle.iam.service.ITokenService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class TokenController {
    @Autowired
    private ITokenService tokenService;

    @ApiOperation(httpMethod = "get", value = "" )
    @RequestMapping(value = "/token/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshToken(HttpServletRequest request){
        String token = request.getHeader("token");
        String userName = request.getHeader("userName");
        Optional<String> newToken = Optional.ofNullable(tokenService.refreshToken(token,userName));
        if(newToken.isPresent()){
            return ResponseEntity.ok(newToken);
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @ApiOperation(httpMethod = "get", value = "" )
    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public ResponseEntity<?> getToken(@RequestParam User user){
        Optional<String> token = Optional.ofNullable(tokenService.generateToken(user));
        if(token.isPresent()){
            return ResponseEntity.ok(token);
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
