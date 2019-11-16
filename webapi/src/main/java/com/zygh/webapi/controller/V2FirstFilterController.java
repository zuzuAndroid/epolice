package com.zygh.webapi.controller;

import com.zygh.common.CommonResult;
import com.zygh.webapi.service.V2FirstFilterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pojo.FirstFilterData;
import pojo.V2FirstFilter;

@RestController
@RequestMapping("/public/v2-first-filter")
@Api(tags = "二期违法初筛", description = "V2FirstFilterController")
public class V2FirstFilterController {

    @Autowired
    V2FirstFilterService v2FirstFilterService;

    @ApiOperation("获取数据")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = V2FirstFilter.class)
    })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object get(@RequestParam int pageNum, @RequestParam int pageSize) {
        return v2FirstFilterService.findAll(pageNum,pageSize);
    }

    @ApiOperation("上一条数据")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FirstFilterData.class)
    })
    @RequestMapping(value = "/prev", method = RequestMethod.GET)
    public Object getPrev() {
        V2FirstFilter data = v2FirstFilterService.prev();

        return new CommonResult().success(data);
    }

    @ApiOperation("下一条数据")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FirstFilterData.class)
    })
    @RequestMapping(value = "/next", method = RequestMethod.GET)
    public Object getNext() {
        V2FirstFilter data = v2FirstFilterService.next();

        return new CommonResult().success(data);
    }

}
