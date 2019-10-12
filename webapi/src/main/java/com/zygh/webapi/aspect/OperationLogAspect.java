package com.zygh.webapi.aspect;


import com.alibaba.fastjson.JSONObject;
import com.zygh.webapi.pojo.LogOperation;
import com.zygh.webapi.service.LogOperationService;
import com.zygh.webapi.service.TokenService;
import com.zygh.webapi.utils.JwtTokenUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

import org.aspectj.lang.reflect.MethodSignature;

@Aspect
@Component
public class OperationLogAspect {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    LogOperationService logOperationService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Pointcut("@annotation(com.zygh.webapi.aspect.OperationLog)")
    public void logPoinCut() {
    }

    //切面 配置通知
    @Before("logPoinCut()")//AfterReturning
    public void saveOperation(JoinPoint joinPoint) {

        //保存日志
        LogOperation operation = new LogOperation();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作--方法上的ViLog的值
        OperationLog log = method.getAnnotation(OperationLog.class);
        if (log != null) {
            //保存操作事件
            String operEvent = log.operEvent();
            operation.setOperEvent(operEvent);

            //保存日志类型
            int operType = log.operType();
            operation.setOperType(operType);

            LOGGER.info("operEvent="+operEvent+",operType="+operType);
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        //操作的url
        String requestURL = request.getRequestURL().toString();
        operation.setOperUrl(requestURL);
        // 客户端ip
        String ip = request.getRemoteAddr();
        operation.setUserIp(ip);
        // 操作人账号、姓名
        String token = tokenService.getTokenByHeader(request);

        if (token != null) {
            String username = jwtTokenUtil.getUserNameFromToken(token);
            operation.setUserName(username);
        }
        /*
        User user = (User) request.getSession().getAttribute(SysUser.SESSION_KEY);
        if(user != null) {
            String account = user.getAccount();
            String username = user.getUsername();
            operation.setIdentity(account);
            operation.setUsername(username);
        }*/

        //操作时间
        operation.setOperDateTime(new Date());

        //调用service保存Operation实体类到数据库
        logOperationService.insert(operation);
    }
}