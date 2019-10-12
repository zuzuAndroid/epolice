package com.zygh.webapi.controller;

import com.zygh.webapi.aspect.OperationLog;
import com.zygh.webapi.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public/role")
@Api(tags = "Role", description = "角色")
public class UserRoleController {

    @Autowired
    UserRoleService userRoleService;

    @ApiOperation("获取角色列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getRoleList() {
        return userRoleService.findAll(1,100);
    }
}
