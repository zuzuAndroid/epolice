package com.zygh.webapi.controller;


import cn.hutool.core.date.DateUtil;
import com.zygh.common.CommonResult;
import com.zygh.webapi.aspect.OperationLog;
import com.zygh.webapi.domain.UserRegisteRequest;
import com.zygh.webapi.pojo.UserAccount;
import com.zygh.webapi.service.UserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/public/user")
@Api(tags = "UserAccount", description = "用户")
public class UserAccountController {

    @Autowired
    UserAccountService userAccountService;


    @ApiOperation("获取用户列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getList(@RequestParam int pageNum, @RequestParam int pageSize) {

        return userAccountService.findAll(pageNum,pageSize);
    }

    @OperationLog(operEvent = "用户信息更新", operType=4)
    @ApiOperation("用户更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody UserRegisteRequest params) {

        if(params.getId().isEmpty()){
            return new CommonResult().validateFailed("用户ID不能为空");
        }

        if(params.getName().trim().isEmpty()){
            return new CommonResult().validateFailed("用户名不能为空");
        }

        try {
            DateUtil.parse(params.getStartTime(),"HH:mm:ss");
        } catch (Exception e) {
            return new CommonResult().validateFailed("时间格式不正确HH:mm:ss");
        }

        try {
            DateUtil.parse(params.getEndTime(),"HH:mm:ss");
        } catch (Exception e) {
            return new CommonResult().validateFailed("时间格式不正确HH:mm:ss");
        }

        UserAccount user = new UserAccount();
        user.setId(params.getId());
        user.setPoliceId(params.getPoliceId());
        user.setDepartment(params.getDepartment());
        user.setRole(params.getRole());
        user.setValidDate(params.getValidDate());
        user.setPwdDate(params.getPwdDate());
        user.setStartTime(params.getStartTime());
        user.setEndTime(params.getEndTime());
        user.setLimitIp(params.getLimitIp());
        user.setStatus(params.getStatus());

        if(userAccountService.isNameExist(user)){
            return new CommonResult().validateFailed("用户名已存在");
        }

        int res = userAccountService.update(user);

        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("用户更新失败");
    }
}
