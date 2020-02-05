package com.zygh.webapi.controller;

import cn.hutool.core.util.StrUtil;
import com.zygh.common.CommonResult;
import com.zygh.webapi.service.UserAccountService;
import dto.UserPasswordDto;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pojo.UserAccount;
import pojo.UserInfo;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Set;

@RestController
@RequestMapping("/user")
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

        UserAccount hasName = userAccountService.checkNickname(params.getNickname());

        if(hasName != null){
            return new CommonResult().validateFailed("用户名已存在");
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

    @ApiOperation("修改用户密码")
    @RequestMapping(value = "/update/password", method = RequestMethod.POST)
    public Object updateUser(@RequestBody UserPasswordDto params) {

        if(StrUtil.isEmpty(String.valueOf(params.getId()))){
            return new CommonResult().validateFailed("id错误");
        }

        if(StrUtil.isEmpty(params.getOldPwd())){
            return new CommonResult().validateFailed("旧密码不能为空");
        }

        if(StrUtil.isEmpty(params.getNewPwd())){
            return new CommonResult().validateFailed("新密码不能为空");
        }

        String pwd = userAccountService.findPwdById(params.getId());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(!passwordEncoder.matches(params.getOldPwd(),pwd)){
            return new CommonResult().validateFailed("密码不匹配");
        }

        params.setNewPwd(passwordEncoder.encode(params.getNewPwd()));

        int res = userAccountService.updateUserPassword(params);

        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("修改用户密码");
    }

    @ApiOperation("删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id", required = true, paramType = "body"),
    })
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Object removeUser(@RequestBody UserAccount params) {

        if(StrUtil.isEmpty(String.valueOf(params.getId()))){
            return new CommonResult().validateFailed("id错误");
        }

        Integer res = userAccountService.remove(params.getId());

        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("删除错误");
    }

    @ApiOperation("用户退出")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Object logout(@RequestBody UserAccount params) {

        if(StrUtil.isEmpty(params.getNickname())){
            return new CommonResult().validateFailed("用户名不能为空");
        }

        System.out.println(params.getNickname()+"用户退出");

        userAccountService.logout();

        return new CommonResult().success();
    }
}
