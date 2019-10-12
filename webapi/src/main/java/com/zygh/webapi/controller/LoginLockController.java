package com.zygh.webapi.controller;


import com.zygh.common.CommonResult;
import com.zygh.webapi.aspect.OperationLog;
import com.zygh.webapi.domain.UserDepartmentRequest;
import com.zygh.webapi.pojo.LoginLock;
import com.zygh.webapi.service.LoginLockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public/login-lock")
@Api(tags = "LoginLock", description = "登陆解锁")
public class LoginLockController {

    @Autowired
    LoginLockService loginLockService;

    @ApiOperation("获取被锁定用户列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getList(@RequestParam int pageNum, @RequestParam int pageSize) {
        return loginLockService.findAll(pageNum,pageSize);
    }

    @OperationLog(operEvent = "解锁被锁定用户", operType=1)
    @ApiOperation("解锁被锁定用户")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody LoginLock params) {

        int res = loginLockService.insert(params);
        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("添加错误");
    }

    @OperationLog(operEvent = "添加被锁定用户", operType=1)
    @ApiOperation("添加被锁定用户")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody LoginLock params) {

        int res = loginLockService.insert(params);
        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("添加错误");
    }
}
