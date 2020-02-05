package com.zygh.webapi.controller;


import cn.hutool.core.util.StrUtil;
import com.zygh.common.CommonResult;
import com.zygh.webapi.domain.UserDepartmentRequest;
import com.zygh.webapi.service.CarBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pojo.CarBrand;

@RestController
@RequestMapping("/car-brand")
@Api(tags = "车标品牌", description = "CarBrandController")
public class CarBrandController {

    @Autowired
    CarBrandService carBrandService;

    @ApiOperation("获取车标品牌列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getList(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                          @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {
        return carBrandService.findAll(pageNum,pageSize);
    }

    @ApiOperation("添加车标品牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code",value = "编码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "name",value = "名称", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody CarBrand params) {

        if(StrUtil.isEmpty(params.getCode())){
            return new CommonResult().validateFailed("编码不能为空");
        }

        if(StrUtil.isEmpty(params.getName())){
            return new CommonResult().validateFailed("名称不能为空");
        }

        int res = carBrandService.add(params);
        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("添加错误");
    }

    @ApiOperation("车标品牌更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "code",value = "编码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "name",value = "名称", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody CarBrand params) {

        if(StrUtil.isEmpty(String.valueOf(params.getCode()))){
            return new CommonResult().validateFailed("编码不能为空");
        }

        if(StrUtil.isEmpty(params.getName())){
            return new CommonResult().validateFailed("名称不能为空");
        }

        int res = carBrandService.update(params);

        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("更新失败");
    }

    @ApiOperation("车标品牌删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Object remove(@RequestBody CarBrand params) {

        if(StrUtil.isEmpty(String.valueOf(params.getId()))){
            return new CommonResult().validateFailed("ID不能为空");
        }

        int res = carBrandService.remove(params.getId());

        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("删除失败");
    }
}
