package com.zygh.webapi.controller;


import com.zygh.webapi.aspect.OperationLog;
import com.zygh.webapi.service.LogOperationService;
import com.zygh.webapi.service.UserLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public/log")
@Api(tags = "Log", description = "日志")
public class LogController {

    @Autowired
    UserLoginLogService userLoginLogService;

    @Autowired
    LogOperationService logOperationService;

    @ApiOperation("登陆日志")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Object getLogin(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                           @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {
        return userLoginLogService.findAll(pageNum,pageSize);
    }

    @ApiOperation("操作日志")
    @RequestMapping(value = "/operation", method = RequestMethod.GET)
    public Object getOperation(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                           @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {
        return logOperationService.findAll(pageNum,pageSize);
    }
}