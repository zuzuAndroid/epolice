package com.zygh.webapi.controller;

import cn.hutool.core.util.StrUtil;
import com.zygh.common.CommonResult;
import com.zygh.webapi.service.LicensePlateTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pojo.CarBrand;
import pojo.LicensePlateType;

@RestController
@RequestMapping("/public/license-type")
@Api(tags = "车牌种类", description = "LicensePlateTypeController")
public class LicensePlateTypeController {

    @Autowired
    LicensePlateTypeService licensePlateTypeService;

    @ApiOperation("获取车牌种类列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getList(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                          @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {
        return licensePlateTypeService.findAll(pageNum,pageSize);
    }

    @ApiOperation("添加车牌种类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code",value = "编码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "name",value = "名称", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody LicensePlateType params) {

        if(StrUtil.isEmpty(String.valueOf(params.getCode()))){
            return new CommonResult().validateFailed("编码不能为空");
        }

        if(StrUtil.isEmpty(params.getName())){
            return new CommonResult().validateFailed("名称不能为空");
        }

        int res = licensePlateTypeService.add(params);

        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("添加错误");
    }

    @ApiOperation("车牌种类更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "code",value = "编码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "name",value = "名称", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody LicensePlateType params) {

        if(StrUtil.isEmpty(String.valueOf(params.getCode()))){
            return new CommonResult().validateFailed("编码不能为空");
        }

        if(StrUtil.isEmpty(params.getName())){
            return new CommonResult().validateFailed("名称不能为空");
        }

        int res = licensePlateTypeService.update(params);

        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("更新失败");
    }
}
