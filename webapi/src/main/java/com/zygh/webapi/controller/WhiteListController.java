package com.zygh.webapi.controller;


import cn.hutool.core.util.StrUtil;
import com.zygh.common.CommonResult;
import com.zygh.webapi.service.WhiteListService;
import dto.FirstFilterDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pojo.WhiteList;

@RestController
@RequestMapping("/public/whitelist")
@Api(tags = "中裕广恒白名单", description = "WhiteListController")
public class WhiteListController {

    @Autowired
    WhiteListService whiteListService;

    @ApiOperation("获取公共列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = WhiteList.class)
    })
    public Object getPublicList(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                          @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {

        return whiteListService.publicFindAll(pageNum,pageSize);
    }

    @ApiOperation("搜索车牌公共列表")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = WhiteList.class)
    })
    public Object searchLicenseNumber(@RequestParam(defaultValue = "",value = "number") String number,
                                      @RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                                @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {

        if (StrUtil.isEmpty(number)) {
            return new CommonResult().validateFailed("请输入车牌号码");
        }

        return whiteListService.publicFindByLicenseNumber(number,pageNum,pageSize);
    }

    @ApiOperation("获取私有列表")
    @RequestMapping(value = "/zygh", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = WhiteList.class)
    })
    public Object getPrivateList(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                          @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {

        return whiteListService.privateFindAll(pageNum,pageSize);
    }

    @ApiOperation("检查号牌是否已存在")
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public Object check(@RequestParam(defaultValue = "",value = "number") String number) {

        if (StrUtil.isEmpty(number)) {
            return new CommonResult().validateFailed("请输入车牌号码");
        }

        int res = whiteListService.exists(number);

        if(res > 0){
            return new CommonResult().success(true);
        }
        return new CommonResult().success(false);
    }

    @ApiOperation("添加白名单")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody WhiteList params) {

        if (StrUtil.isEmpty(params.getLicenseNumber())) {
            return new CommonResult().validateFailed("请输入车牌号码");
        }

        if(whiteListService.exists(params.getLicenseNumber()) > 1){
            return new CommonResult().validateFailed("车牌号码已存在");
        }

        int res = whiteListService.add(params);

        if(res > 0){
            return new CommonResult().success();
        }
        return new CommonResult().validateFailed("添加失败");
    }
}
