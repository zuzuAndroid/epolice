package com.zygh.webapi.controller;


import cn.hutool.core.util.StrUtil;
import com.zygh.common.CommonResult;
import com.zygh.webapi.service.HxWhiteListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pojo.HxWhiteList;

@RestController
@RequestMapping("/public/hx-white-list")
@Api(tags = "海信白名单", description = "HxWhiteListController")
public class HxWhiteListController {

    @Autowired
    HxWhiteListService hxWhiteListService;

    @ApiOperation("获取列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = HxWhiteList.class)
    })
    public Object getList(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                                @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {

        return hxWhiteListService.findAll(pageNum,pageSize);
    }

    @ApiOperation("按车牌号码搜索")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = HxWhiteList.class)
    })
    public Object search(@RequestParam(defaultValue = "",value = "number") String number,
                         @RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                          @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {

        return hxWhiteListService.findByLicenseNumber(number,pageNum,pageSize);
    }

    @ApiOperation("检查号牌是否已存在")
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public Object check(@RequestParam(defaultValue = "",value = "number") String number) {

        if (StrUtil.isEmpty(number)) {
            return new CommonResult().validateFailed("请输入车牌号码");
        }

        int res = hxWhiteListService.exists(number);

        if(res > 0){
            return new CommonResult().success(true);
        }
        return new CommonResult().success(false);
    }
}
