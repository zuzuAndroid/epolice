package com.zygh.webapi.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.zygh.common.CommonResult;
import com.zygh.webapi.aspect.OperationLog;
import com.zygh.webapi.domain.UserDepartmentRequest;
import com.zygh.webapi.domain.UserRegisteRequest;
import com.zygh.webapi.pojo.UserDepartment;
import com.zygh.webapi.service.UserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pojo.UserAccount;
import pojo.UserInfo;

import java.util.Date;

@RestController
@RequestMapping("/public/user")
@Api(tags = "用户", description = "UserAccountController")
public class UserAccountController {

    @Autowired
    UserAccountService userAccountService;

    @ApiOperation("获取用户列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserInfo.class)
    })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getList(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                          @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {

        return userAccountService.findAll(pageNum,pageSize);
    }

    @ApiOperation("添加用户")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addUser(@RequestBody UserAccount params){
        if(StrUtil.isEmpty(params.getName())){
            return new CommonResult().validateFailed("用户姓名不能为空");
        }

        if(StrUtil.isEmpty(params.getNickname())){
            return new CommonResult().validateFailed("用户名不能为空");
        }

        //默认密码
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = "888888";
        params.setPassword(passwordEncoder.encode(password));

        int res = userAccountService.addUser(params);

        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("添加用户错误");
    }

    @ApiOperation("用户更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateUser(@RequestBody UserAccount params) {

        if(StrUtil.isEmpty(String.valueOf(params.getId()))){
            return new CommonResult().validateFailed("id错误");
        }

        int res = userAccountService.updateUser(params);

        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("更新错误");
    }



}
