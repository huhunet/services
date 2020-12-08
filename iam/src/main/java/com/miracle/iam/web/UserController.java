package com.miracle.iam.web;

import com.miracle.iam.domain.entity.User;
import com.miracle.iam.domain.vo.ErrorResponse;
import com.miracle.iam.service.IUserService;
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

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@Api(value = "8:用户登陆接口", description = "用户登陆",position = 2,produces = "application/json")
@RequestMapping(value="/user")
public class UserController {
    @Autowired
    IUserService userService;

    @ApiOperation(httpMethod = "GET", value = "" )
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<User> get(@RequestParam String userName){
        Optional<User> users = Optional.ofNullable(userService.getUserByName(userName));
        if(users.isPresent()){
            return ResponseEntity.ok(users.get());
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(httpMethod = "GET", value = "" )
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<User>> list(){
        Optional<List<User>> users = Optional.ofNullable(userService.list());
        if(users.isPresent()){
            return ResponseEntity.ok(users.get());
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(httpMethod = "POST", value = "" )
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<ErrorResponse> add(){
        return null;
    }

    @ApiOperation(httpMethod = "POST", value = "" )
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<ErrorResponse> delete(){
        return null;
    }
}
