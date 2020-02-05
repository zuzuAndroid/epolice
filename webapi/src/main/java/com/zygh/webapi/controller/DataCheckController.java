package com.zygh.webapi.controller;

import cn.hutool.core.util.StrUtil;
import com.zygh.common.CommonResult;
import com.zygh.webapi.service.ReviewFilterService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pojo.FirstFilterData;

@RestController
@RequestMapping("/public/check")
@Api(tags = "数据运维检测", description = "DataCheckController")
public class DataCheckController {

    @Autowired
    ReviewFilterService reviewFilterService;

    @ApiOperation("检查违法复核重复数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "datetime",value = "日期", required = true, paramType = "query"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FirstFilterData.class)
    })
    @RequestMapping(value = "/wffh", method = RequestMethod.GET)
    public Object checkDuplicate(@RequestParam String datetime) {

        if(StrUtil.isEmpty(datetime)){
            return new CommonResult().validateFailed("日期不能为空");
        }

        int count = reviewFilterService.checkDuplicate(datetime);

        if(count > 0){
            return new CommonResult().validateFailed("发现"+count+"条重复数据");
        }

        return new CommonResult().success("没有重复数据！");
    }

    @ApiOperation("标记违法复核重复数据")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FirstFilterData.class)
    })
    @RequestMapping(value = "/update-wffh", method = RequestMethod.GET)
    public Object updateDuplicate() {

        Integer res = reviewFilterService.updateDuplicate();

        if(res > 0){
            return new CommonResult().validateFailed("发现"+reviewFilterService.findDuplicate()+"条重复数据");
        }

        return new CommonResult().success("没有重复数据");
    }

    @ApiOperation("删除违法复核重复数据")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FirstFilterData.class)
    })
    @RequestMapping(value = "/delete-wffh", method = RequestMethod.GET)
    public Object deleteDuplicate() {

        Integer res = reviewFilterService.cleanDuplicate();

        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("删除失败");
    }
}
