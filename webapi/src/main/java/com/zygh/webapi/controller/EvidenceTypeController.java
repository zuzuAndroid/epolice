package com.zygh.webapi.controller;


import cn.hutool.core.util.StrUtil;
import com.zygh.common.CommonResult;
import com.zygh.webapi.service.EvidenceTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pojo.CarBrand;
import pojo.EvidenceType;

@RestController
@RequestMapping("/evidence-type")
@Api(tags = "证据类型", description = "EvidenceTypeController")
public class EvidenceTypeController {

    @Autowired
    EvidenceTypeService evidenceTypeService;

    @ApiOperation("获取证据类型列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getList(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                          @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {
        return evidenceTypeService.findAll(pageNum,pageSize);
    }

    @ApiOperation("添加证据类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code",value = "编码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "name",value = "名称", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody EvidenceType params) {

        if(StrUtil.isEmpty(params.getCode())){
            return new CommonResult().validateFailed("编码不能为空");
        }

        if(StrUtil.isEmpty(params.getName())){
            return new CommonResult().validateFailed("名称不能为空");
        }

        int res = evidenceTypeService.add(params);
        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("添加错误");
    }

    @ApiOperation("证据类型更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "code",value = "编码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "name",value = "名称", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody EvidenceType params) {

        if(StrUtil.isEmpty(String.valueOf(params.getCode()))){
            return new CommonResult().validateFailed("编码不能为空");
        }

        if(StrUtil.isEmpty(params.getName())){
            return new CommonResult().validateFailed("名称不能为空");
        }

        int res = evidenceTypeService.update(params);

        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("更新失败");
    }

    @ApiOperation("证据类型删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Object remove(@RequestBody CarBrand params) {

        if(StrUtil.isEmpty(String.valueOf(params.getId()))){
            return new CommonResult().validateFailed("ID不能为空");
        }

        int res = evidenceTypeService.remove(params.getId());

        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("删除失败");
    }
}
