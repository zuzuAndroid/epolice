package com.zygh.webapi.controller;

import com.zygh.common.CommonResult;
import com.zygh.webapi.aspect.OperationLog;
import com.zygh.webapi.domain.UserDepartmentRequest;
import com.zygh.webapi.pojo.LoginSetup;
import com.zygh.webapi.service.LoginSetupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/login-setup")
@Api(tags = "loginSetup", description = "用户登陆设置")
public class LoginSetupController {

    @Autowired
    LoginSetupService loginSetupService;

    @ApiOperation("获取设置")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object get() {
        LoginSetup setup = loginSetupService.find();

        return new CommonResult().success(setup);
    }

    @OperationLog(operEvent = "用户登陆设置更新", operType=4)
    @ApiOperation("设置更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateDepartment(@RequestBody LoginSetup params) {

        if(params.getPwdLevel() <= 0){
            return new CommonResult().validateFailed("密码级别参数错误");
        }

        if(params.getUserLock() <= 0){
            return new CommonResult().validateFailed("锁定用户的登陆次数参数错误");
        }

        int res = loginSetupService.update(params);
        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("更新错误");
    }
}
