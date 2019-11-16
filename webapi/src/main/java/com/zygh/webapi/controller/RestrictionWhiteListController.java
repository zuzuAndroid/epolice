package com.zygh.webapi.controller;

import cn.hutool.core.util.StrUtil;
import com.zygh.common.CommonResult;
import com.zygh.webapi.service.RestrictionWhiteListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pojo.RestrictionWhiteList;
import pojo.WhiteList;

@RestController
@RequestMapping("/public/restriction")
@Api(tags = "限行白名单", description = "RestrictionWhiteListController")
public class RestrictionWhiteListController {

    @Autowired
    RestrictionWhiteListService restrictionWhiteListService;

    @ApiOperation("获取限行列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RestrictionWhiteList.class)
    })
    public Object getList(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                                @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {

        return restrictionWhiteListService.findAll(pageNum,pageSize);
    }

    @ApiOperation("按车牌号码搜索")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RestrictionWhiteList.class)
    })
    public Object search(@RequestParam(defaultValue = "",value = "number") String number,
                        @RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                          @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {

        if (StrUtil.isEmpty(number)) {
            return new CommonResult().validateFailed("请输入车牌号码");
        }

        return restrictionWhiteListService.findByLicenseNumber(number,pageNum,pageSize);
    }

    @ApiOperation("检查号牌是否已存在")
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = WhiteList.class)
    })
    public Object check(@RequestParam(defaultValue = "",value = "number") String number) {

        if (StrUtil.isEmpty(number)) {
            return new CommonResult().validateFailed("请输入车牌号码");
        }

        int res = restrictionWhiteListService.exists(number);

        if(res > 0){
            return new CommonResult().success(true);
        }
        return new CommonResult().success(false);
    }
}
