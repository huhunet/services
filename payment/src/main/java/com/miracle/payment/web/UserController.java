package com.miracle.payment.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(value = "8:用户登陆接口", description = "用户登陆",position = 2,produces = "application/json")
@RequestMapping(value="/user")
public class UserController {

    @ApiOperation(httpMethod = RequestMethod.GET, value = "" )
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Response get(){

    }

    @ApiOperation(httpMethod = "POST", value = "" )
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Response list(){

    }

    @ApiOperation(httpMethod = "POST", value = "" )
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Response add(){

    }

    @ApiOperation(httpMethod = "POST", value = "" )
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Response delete(){

    }
}
