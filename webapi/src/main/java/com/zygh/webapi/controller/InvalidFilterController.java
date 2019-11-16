package com.zygh.webapi.controller;

import com.zygh.webapi.service.InvalidFilterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pojo.InvalidFilter;

@RestController
@RequestMapping("/public/invalid-filter")
@Api(tags = "机筛废片", description = "InvalidFilterController")
public class InvalidFilterController {

    @Autowired
    InvalidFilterService invalidFilterService;

    @ApiOperation("获取用户已提取的数据")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InvalidFilter.class)
    })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object get(@RequestParam String username, @RequestParam int pageNum, @RequestParam int pageSize) {
        return invalidFilterService.findByUserName(username,pageNum,pageSize);
    }
}
