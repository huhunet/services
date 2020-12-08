package com.miracle.iam.web;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(value = "8:用户登陆接口", description = "用户登陆",position = 2,produces = "application/json")
@RequestMapping(value="/user")
public class PermissionController {
}
