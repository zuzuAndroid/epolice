package com.zygh.webapi.controller;

import cn.hutool.core.util.StrUtil;
import com.zygh.common.CommonResult;
import com.zygh.webapi.service.RestrictionWhiteListService;
import com.zygh.webapi.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pojo.RestrictionWhiteList;
import pojo.WhiteList;

import java.util.Date;

@RestController
@RequestMapping("/public/restriction")
@Api(tags = "限行白名单（1号库）", description = "RestrictionWhiteListController")
public class RestrictionWhiteListController {

    @Autowired
    RestrictionWhiteListService restrictionWhiteListService;

    @ApiOperation("获取限行列表（1号库）")
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

    @ApiOperation("按备注搜索")
    @RequestMapping(value = "/remark", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RestrictionWhiteList.class)
    })
    public Object searchForRemark(@RequestParam(defaultValue = "",value = "value") String value,
                                  @RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                                  @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {

        if (StrUtil.isEmpty(value)) {
            return new CommonResult().validateFailed("请输入关键字");
        }

        return restrictionWhiteListService.findByRemark(value,pageNum,pageSize);
    }

    @ApiOperation("检查号牌是否已存在")
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RestrictionWhiteList.class)
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

    @ApiOperation("添加白名单")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody RestrictionWhiteList params) {

        if (StrUtil.isEmpty(params.getLicenseNumber())) {
            return new CommonResult().validateFailed("请输入车牌号码");
        }

        if (StrUtil.isEmpty(params.getRemark())) {
            return new CommonResult().validateFailed("请输入备注");
        }

        if(!DateUtil.isValidDate(params.getStartDate(),"yyyy-MM-dd")){
            return new CommonResult().validateFailed("请输入正确日期格式yyyy-MM-dd");
        }

        if(!DateUtil.isValidDate(params.getEndDate(),"yyyy-MM-dd")){
            return new CommonResult().validateFailed("请输入正确日期格式yyyy-MM-dd");
        }

        if(restrictionWhiteListService.exists(params.getLicenseNumber()) > 1){
            return new CommonResult().validateFailed("车牌号码已存在");
        }

        params.setCreateDate(new Date());

        int res = restrictionWhiteListService.add(params);

        if(res > 0){
            return new CommonResult().success();
        }
        return new CommonResult().validateFailed("添加失败");
    }

    @ApiOperation("修改白名单")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody RestrictionWhiteList params) {

        if(StrUtil.isEmpty(String.valueOf(params.getId()))){
            return new CommonResult().validateFailed("id不能为空");
        }

        if (StrUtil.isEmpty(params.getLicenseNumber())) {
            return new CommonResult().validateFailed("请输入车牌号码");
        }

        if (StrUtil.isEmpty(params.getRemark())) {
            return new CommonResult().validateFailed("请输入备注");
        }

        if(!DateUtil.isValidDate(params.getStartDate(),"yyyy-MM-dd")){
            return new CommonResult().validateFailed("请输入正确日期格式yyyy-MM-dd");
        }

        if(!DateUtil.isValidDate(params.getEndDate(),"yyyy-MM-dd")){
            return new CommonResult().validateFailed("请输入正确日期格式yyyy-MM-dd");
        }

        if(restrictionWhiteListService.exists(params.getLicenseNumber()) > 1){
            return new CommonResult().validateFailed("车牌号码已存在");
        }

        int res = restrictionWhiteListService.update(params);

        if(res > 0){
            return new CommonResult().success();
        }
        return new CommonResult().validateFailed("修改失败");
    }

    @ApiOperation("删除白名单")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Object remove(@RequestBody RestrictionWhiteList params) {

        if(StrUtil.isEmpty(String.valueOf(params.getId()))){
            return new CommonResult().validateFailed("id不能为空");
        }

        int res = restrictionWhiteListService.remove(params.getId());

        if(res > 0){
            return new CommonResult().success();
        }
        return new CommonResult().validateFailed("删除失败");
    }
}
