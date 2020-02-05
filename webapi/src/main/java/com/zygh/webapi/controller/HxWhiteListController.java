package com.zygh.webapi.controller;


import cn.hutool.core.util.StrUtil;
import com.zygh.common.CommonResult;
import com.zygh.webapi.service.HxWhiteListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pojo.HxWhiteList;
import pojo.RestrictionWhiteList;
import pojo.WhiteList;

import java.util.Date;

@RestController
@RequestMapping("/public/hx-white-list")
@Api(tags = "海信白名单（2号库）", description = "HxWhiteListController")
public class HxWhiteListController {

    @Autowired
    HxWhiteListService hxWhiteListService;

    @ApiOperation("获取列表（2号库）")
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

    @ApiOperation("添加白名单")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody HxWhiteList params) {

        if (StrUtil.isEmpty(params.getLicenseNumber())) {
            return new CommonResult().validateFailed("请输入车牌号码");
        }

        if(hxWhiteListService.exists(params.getLicenseNumber()) > 1){
            return new CommonResult().validateFailed("车牌号码已存在");
        }

        if (StrUtil.isEmpty(params.getAddUser())) {
            return new CommonResult().validateFailed("请输入添加人");
        }

        if (StrUtil.isEmpty(params.getRemark())) {
            return new CommonResult().validateFailed("请输入备注");
        }


        params.setCreateDate(new Date());

        int res = hxWhiteListService.add(params);

        if(res > 0){
            return new CommonResult().success();
        }
        return new CommonResult().validateFailed("添加失败");
    }

    @ApiOperation("修改白名单")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody HxWhiteList params) {

        if(StrUtil.isEmpty(String.valueOf(params.getId()))){
            return new CommonResult().validateFailed("id不能为空");
        }

        if (StrUtil.isEmpty(params.getLicenseNumber())) {
            return new CommonResult().validateFailed("请输入车牌号码");
        }

        if (StrUtil.isEmpty(params.getRemark())) {
            return new CommonResult().validateFailed("请输入备注");
        }

        if(hxWhiteListService.exists(params.getLicenseNumber()) > 1){
            return new CommonResult().validateFailed("车牌号码已存在");
        }

        int res = hxWhiteListService.update(params);

        if(res > 0){
            return new CommonResult().success();
        }
        return new CommonResult().validateFailed("修改失败");
    }

    @ApiOperation("删除白名单")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Object remove(@RequestBody HxWhiteList params) {

        if(StrUtil.isEmpty(String.valueOf(params.getId()))){
            return new CommonResult().validateFailed("id不能为空");
        }

        int res = hxWhiteListService.remove(params.getId());

        if(res > 0){
            return new CommonResult().success();
        }
        return new CommonResult().validateFailed("删除失败");
    }
}
