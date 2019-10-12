package com.zygh.webapi.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import com.zygh.common.CommonResult;
import com.zygh.webapi.aspect.OperationLog;
import com.zygh.webapi.domain.UserLoginRequest;
import com.zygh.webapi.domain.UserRegisteRequest;
import com.zygh.webapi.pojo.*;
import com.zygh.webapi.service.*;
import com.zygh.webapi.utils.IPUtil;
import com.zygh.webapi.utils.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Api(tags = "AuthController", description = "用户登陆身份验证")
public class AuthController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserLoginLogService userLoginLogService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private LoginSetupService loginSetupService;

    @Autowired
    private LoginLockService loginLockService;

    @ApiOperation("用户名和密码登陆")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserAccount.class, responseContainer = "user"),
            @ApiResponse(code = 405, message = "账号名或密码错误")
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object userNameLogin(@RequestBody UserLoginRequest params,HttpServletRequest request) {

        if(params.getName().isEmpty() || params.getPwd().isEmpty()){
            return new CommonResult().validateFailed("请输入用户名和密码");
        }

        LoginSetup setup = loginSetupService.find();

        loginAttemptService.setUserLockNumber(setup.getUserLock());
        loginAttemptService.setTerminalLockNumber(setup.getTerminalLock());

        String ip = IPUtil.getIpAddr(request);
        LOGGER.info("ip==》",ip);
        if (loginAttemptService.isBlockedByName(params.getName())) {
            LoginLock lock = new LoginLock();
            lock.setLockType(1);
            lock.setLockContent(params.getName());
            lock.setLocked(true);
            lock.setDateTime(new Date());
            loginLockService.insert(lock);

            CommonResult result = new CommonResult();
            result.setCode(ErrorCode.USER_LOGIN_LOCKED);
            result.setMessage("用户登陆被锁定");
            return result;
        }

        if (loginAttemptService.isBlockedByIp(ip)) {
            LoginLock lock = new LoginLock();
            lock.setLockType(2);
            lock.setLockContent(ip);
            lock.setLocked(true);
            lock.setDateTime(new Date());
            loginLockService.insert(lock);

            CommonResult result = new CommonResult();
            result.setCode(ErrorCode.TERMINAL_LOGIN_LOCKED);
            result.setMessage("终端登陆被锁定");
            return result;
        }

        String token = tokenService.getTokenByName(params.getName(), params.getPwd());
        LOGGER.info("token:" + token);

        UserLoginLog log = new UserLoginLog();
        log.setLoginIp(ip);
        log.setLoginDateTime(new Date());

        if (token != null) {
            //userAccountService.updateByUserName(params.getName(), new Date());
            UserAccount user = userAccountService.findOne(params.getName());


            Map<String, Object> data = new HashMap<>(16);
            data.put("token", token);
            data.put("user",user);
            //data.put("token_expired_date", DateUtil.formatTime24(jwtTokenUtil.getExpiredDateFromToken(token)));

            //登陆日志
            log.setUserAccountId(user.getId());
            log.setLoginName(user.getName());
            log.setUserRoleName(user.getRoleName());
            log.setLoginMsg("登陆成功");

            //String check_text = user.getName()+","+log.getLoginIp()+","+log.getLoginDateTime()+","+log.getLoginMsg();
            String check_text = log.getFieldsForCheckBit();
            String check = Base64.encode(check_text);
            log.setCheckBit(check);

            userLoginLogService.insert(log);

            loginAttemptService.loginSucceeded(params.getName());
            loginAttemptService.loginSucceeded(ip);

            return new CommonResult().success(data);
        }

        /**
         * 登陆错误处理
         */
        loginAttemptService.loginFailed(params.getName());//锁定用户计数
        loginAttemptService.loginFailed(ip);//锁定终端计数

        log.setLoginName(params.getName());
        log.setLoginMsg("登陆失败: 用户名：" + params.getName()+ "密码：" + params.getPwd());
        String check_text = params.getName()+","+log.getLoginIp()+","+log.getLoginDateTime()+","+log.getLoginMsg();
        String check = Base64.encode(check_text);
        log.setCheckBit(check);
        userLoginLogService.insert(log);

        CommonResult fail = new CommonResult();
        fail.setCode(ErrorCode.LOGIN_FAIL);
        fail.setMessage("登陆失败,用户名和密码不匹配");

        Map<String, Object> data = new HashMap<>(16);
        data.put("userLock", loginAttemptService.getRestNumber(params.getName()));
        data.put("terminalLock",loginAttemptService.getRestNumber(ip));

        fail.setData(data);

        return fail;
    }

    @ApiOperation("用户登出")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Object userLogout(HttpServletRequest request) {
        String token = tokenService.getTokenByHeader(request);

        if (token != null) {
            String username = jwtTokenUtil.getUserNameFromToken(token);
            LOGGER.info("username:" + username);

            return new CommonResult().success();
        }
        return new CommonResult().validateFailed("登陆用户不存在");
    }

    @OperationLog(operEvent = "用户注册", operType=1)
    @ApiOperation("用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object register(@RequestBody UserRegisteRequest params) {
        if(params.getName().isEmpty()){
            return new CommonResult().validateFailed("用户名不能为空");
        }

        if(params.getIdCard().isEmpty()){
            return new CommonResult().validateFailed("身份证不能为空");
        }/*else if(!IdcardUtil.isValidCard(params.getIdCard())){
            return new CommonResult().validateFailed("身份证不正确");
        }*/

        try {
            DateUtil.parse(params.getStartTime().toString(),"HH:mm:ss");
        } catch (Exception e) {
            return new CommonResult().validateFailed("时间格式不正确,请使用06:00:00");
        }

        try {
            DateUtil.parse(params.getEndTime().toString(),"HH:mm:ss");
        } catch (Exception e) {
            return new CommonResult().validateFailed("时间格式不正确,请使用22:00:00");
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        UserAccount user = new UserAccount();
        user.setId(IdUtil.objectId());
        user.setName(params.getName());
        user.setIdCard(params.getIdCard());
        user.setPoliceId(params.getPoliceId());
        user.setDepartment(params.getDepartment());
        user.setRole(params.getRole());
        user.setPwd(bCryptPasswordEncoder.encode(params.getPwd()));
        user.setValidDate(params.getValidDate());
        user.setPwdDate(params.getPwdDate());
        user.setStartTime(params.getStartTime());
        user.setEndTime(params.getEndTime());
        user.setCreateDateTime(new Date());

        if(userAccountService.isNameExist(user)){
            return new CommonResult().validateFailed("用户名已存在");
        }

        if(userAccountService.isIdCardExist(user)){
            return new CommonResult().validateFailed("身份证号码已存在");
        }

        if(userAccountService.save(user) > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("用户添加失败");
    }
}
