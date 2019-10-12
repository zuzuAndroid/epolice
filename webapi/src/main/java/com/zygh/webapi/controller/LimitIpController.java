package com.zygh.webapi.controller;

import com.zygh.common.CommonResult;
import com.zygh.webapi.aspect.OperationLog;
import com.zygh.webapi.domain.UserRegisteRequest;
import com.zygh.webapi.pojo.LimitIp;
import com.zygh.webapi.service.LimitIpService;
import com.zygh.webapi.utils.IPUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public/limitip")
@Api(tags = "LimitIp", description = "IP限制")
public class LimitIpController {

    @Autowired
    LimitIpService limitIpService;


    @ApiOperation("获取列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getList() {
        return limitIpService.findAll(1,100);
    }

    @OperationLog(operEvent = "添加登陆限制IP", operType=1)
    @ApiOperation("添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody LimitIp params) {
        if(params.getIp().isEmpty()){
            return new CommonResult().validateFailed("不能为空");
        }

        if(IPUtil.isValide(params.getIp()) == false){
            return new CommonResult().validateFailed("IP地址不正确");
        }

        if(limitIpService.insert(params.getIp()) > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("添加失败");
    }

    @OperationLog(operEvent = "更新登陆限制IP", operType=4)
    @ApiOperation("更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody LimitIp params) {

        if(params.getId() <= 0){
            return new CommonResult().validateFailed("ID不能为空");
        }

        if(params.getIp().isEmpty()){
            return new CommonResult().validateFailed("不能为空");
        }

        if(IPUtil.isValide(params.getIp()) == false){
            return new CommonResult().validateFailed("IP地址不正确");
        }

        if(limitIpService.update(params) > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("更新失败");
    }
}
