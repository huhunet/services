package com.miracle.iam.web;

import com.miracle.iam.service.IAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
@Api(value = "8:用户登陆接口", description = "用户登陆",position = 2,produces = "application/json")
@RequestMapping(value="/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @ApiOperation(httpMethod = "post", value = "" )
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestParam String userName,@RequestParam String password){
        Optional<String> token = Optional.ofNullable(authService.login(userName,password));
        if(token.isPresent()){
            return ResponseEntity.ok(token);
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
