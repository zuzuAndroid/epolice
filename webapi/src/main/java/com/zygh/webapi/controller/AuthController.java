package com.zygh.webapi.controller;

import cn.hutool.core.util.StrUtil;
import com.zygh.common.CommonResult;
import com.zygh.webapi.aspect.NoRepeatSubmit;
import com.zygh.webapi.domain.UserLoginRequest;
import com.zygh.webapi.service.*;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pojo.UserInfo;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@Api(tags = "用户登陆身份验证", description = "AuthController")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserAccountService userAccountService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @ApiOperation("用户名和密码登陆，获取Token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CommonResult.class)
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object userNameLogin(@RequestBody UserLoginRequest params) {
        if(StrUtil.isEmpty(params.getName())){
            return new CommonResult().validateFailed("用户名不能为空");
        }

        if(StrUtil.isEmpty(params.getPwd())){
            return new CommonResult().validateFailed("密码不能为空");
        }

        String token = tokenService.getTokenByName(params.getName(), params.getPwd());

        if (token != null) {
            UserInfo user = userAccountService.getUserInfo(params.getName());

            Map<String, Object> data = new HashMap<>(16);
            data.put("user", user);
            data.put("token", token);

            return new CommonResult().success(data);
        }

        return new CommonResult().validateFailed("登陆失败,用户名和密码不匹配");
    }

    @RequestMapping(value = "/cache-clean", method = RequestMethod.GET)
    public void clean() {
        Set<String> keys = redisTemplate.keys("*");
        Iterator<String> it1 = keys.iterator();
        while (it1.hasNext()) {
            redisTemplate.delete(it1.next());
        }
    }

}
