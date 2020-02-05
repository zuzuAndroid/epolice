package com.zygh.webapi.controller;


import cn.hutool.core.util.StrUtil;
import com.zygh.common.CommonResult;
import com.zygh.webapi.service.CarColorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pojo.CarBrand;
import pojo.CarColor;

@RestController
@RequestMapping("/car-color")
@Api(tags = "车身颜色", description = "CarColorController")
public class CarColorController {

    @Autowired
    CarColorService carColorService;

    @ApiOperation("获取车身颜色列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getList(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                          @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {
        return carColorService.findAll(pageNum,pageSize);
    }

    @ApiOperation("添加车身颜色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code",value = "编码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "name",value = "名称", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody CarColor params) {

        if(StrUtil.isEmpty(params.getCode())){
            return new CommonResult().validateFailed("编码不能为空");
        }

        if(StrUtil.isEmpty(params.getName())){
            return new CommonResult().validateFailed("名称不能为空");
        }

        int res = carColorService.add(params);
        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("添加错误");
    }

    @ApiOperation("车身颜色更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "code",value = "编码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "name",value = "名称", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody CarColor params) {

        if(StrUtil.isEmpty(String.valueOf(params.getCode()))){
            return new CommonResult().validateFailed("编码不能为空");
        }

        if(StrUtil.isEmpty(params.getName())){
            return new CommonResult().validateFailed("名称不能为空");
        }

        int res = carColorService.update(params);

        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("更新失败");
    }

    @ApiOperation("车身颜色删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Object remove(@RequestBody CarColor params) {

        if(StrUtil.isEmpty(String.valueOf(params.getId()))){
            return new CommonResult().validateFailed("ID不能为空");
        }

        int res = carColorService.remove(params.getId());

        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("删除失败");
    }
}
