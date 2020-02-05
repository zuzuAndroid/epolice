package com.zygh.webapi.controller;


import cn.hutool.core.util.StrUtil;
import com.zygh.common.CommonResult;
import com.zygh.webapi.service.IllegalDataSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pojo.IllegalDataSource;

@RestController
@RequestMapping("/illegal-datasource")
@Api(tags = "违法数据来源代码", description = "IllegalDataSourceController")
public class IllegalDataSourceController {

    @Autowired
    IllegalDataSourceService illegalDataSourceService;

    @ApiOperation("获取违法数据来源代码")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getList(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                          @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {
        return illegalDataSourceService.findAll(pageNum,pageSize);
    }

    @ApiOperation("添加违法数据来源代码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code",value = "编码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "name",value = "名称", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody IllegalDataSource illegalDataSource) {

        if(StrUtil.isEmpty(illegalDataSource.getCode())){
            return new CommonResult().validateFailed("编码不能为空");
        }

        if(StrUtil.isEmpty(illegalDataSource.getName())){
            return new CommonResult().validateFailed("名称不能为空");
        }
        int res = illegalDataSourceService.add(illegalDataSource);
        if(res > 0){
            return new CommonResult().success();
        }
        return new CommonResult().validateFailed("添加错误");
    }

    @ApiOperation("违法数据来源代码更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "code",value = "编码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "name",value = "名称", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody IllegalDataSource illegalDataSource) {
        if(StrUtil.isEmpty(String.valueOf(illegalDataSource.getCode()))){
            return new CommonResult().validateFailed("编码不能为空");
        }
        if(StrUtil.isEmpty(illegalDataSource.getName())){
            return new CommonResult().validateFailed("名称不能为空");
        }
        int res = illegalDataSourceService.update(illegalDataSource);
        if(res > 0){
            return new CommonResult().success();
        }
        return new CommonResult().validateFailed("更新失败");
    }


}
