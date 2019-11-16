package com.zygh.webapi.controller;

import cn.hutool.core.util.StrUtil;
import com.zygh.common.CommonResult;
import com.zygh.webapi.service.FirstFilterService;
import dto.FirstFilterDto;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pojo.FirstFilterData;


/**
 * 违法初筛
 */
@RestController
@RequestMapping("/public/first-filter")
@Api(tags = "违法初筛", description = "FirstFilterController")
public class FirstFilterController {

    @Autowired(required=false)
    FirstFilterService firstFilterService;

    @ApiOperation("获取用户已提取的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum",value = "当前页码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量", required = true, paramType = "query"),
            @ApiImplicitParam(name = "token",value = "token", required = true, paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FirstFilterData.class)
    })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object get(@RequestParam String username, @RequestParam int pageNum, @RequestParam int pageSize) {
        return firstFilterService.findByUserName(username,pageNum,pageSize);
    }

    @ApiOperation("根据条件查询数据")
    @RequestMapping(value = "/query-filter", method = RequestMethod.POST)
    public Object queryFilter(@RequestBody FirstFilterDto params) {
        int pageNum = 1;
        int pageSize = 10;

        pageNum = params.getPageNum();
        pageSize = params.getPageSize();

        if(StrUtil.isEmpty(params.getStartDate())){
            return new CommonResult().validateFailed("开始时间不能为空");
        }

        if(StrUtil.isEmpty(params.getEndDate())){
            return new CommonResult().validateFailed("结束时间不能为空");
        }

        return firstFilterService.queryForFilter(params,pageNum,pageSize);
    }

    @ApiOperation("违法数据查询")
    @RequestMapping(value = "/query-search", method = RequestMethod.POST)
    public Object querySearch(@RequestBody FirstFilterDto params) {
        int pageNum = params.getPageNum();
        int pageSize = params.getPageSize();
        return firstFilterService.queryForSearch(params,pageNum,pageSize);
    }

    @ApiOperation("上一条数据")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FirstFilterData.class)
    })
    @RequestMapping(value = "/prev/{id}", method = RequestMethod.GET)
    public Object getPrev(@PathVariable(value="id") int id,@RequestParam String username) {
        FirstFilterData data = firstFilterService.prev(id,username);

        return new CommonResult().success(data);
    }

    @ApiOperation("下一条数据")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FirstFilterData.class)
    })
    @RequestMapping(value = "/next/{id}", method = RequestMethod.GET)
    public Object getNext(@PathVariable(value="id") int id,@RequestParam String username) {
        FirstFilterData data = firstFilterService.next(id,username);

        return new CommonResult().success(data);
    }

    @ApiOperation("通过")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CommonResult.class)
    })
    @RequestMapping(value = "/pass/{id}",method = RequestMethod.POST)
    public Object pass(@PathVariable(value="id") int id) {

        if(StrUtil.isEmpty(String.valueOf(id))){
            return new CommonResult().validateFailed("ID不能为空");
        }

        int res = firstFilterService.updatePass(id);
        int addTo = firstFilterService.addToWffh(id);

        if(res > 0 && addTo > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("通过失败");
    }

    @ApiOperation("废弃")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CommonResult.class)
    })
    @RequestMapping(value = "/invalid/{id}",method = RequestMethod.POST)
    public Object invalid(@PathVariable(value="id") int id) {
        if(StrUtil.isEmpty(String.valueOf(id))){
            return new CommonResult().validateFailed("ID不能为空");
        }

        int res = firstFilterService.updateInvalid(id);

        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("更新失败");
    }

    @ApiOperation("提取数据")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CommonResult.class)
    })
    @RequestMapping(value = "/pickup", method = RequestMethod.POST)
    public Object pickup(@RequestBody FirstFilterDto params) {

        if(StrUtil.isEmpty(params.getUsername())){
            return new CommonResult().validateFailed("用户名不能为空");
        }

        int res = firstFilterService.pickUp(params);

        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("提取失败");
    }

    @ApiOperation("释放数据")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CommonResult.class)
    })
    @RequestMapping(value = "/release", method = RequestMethod.POST)
    public Object release(@RequestBody FirstFilterDto params) {
        if(StrUtil.isEmpty(params.getUsername())){
            return new CommonResult().validateFailed("用户名不能为空");
        }

        int res = firstFilterService.release(params.getUsername());

        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("释放失败");
    }

    @ApiOperation("初筛统计")
    @RequestMapping(value = "/count", method = RequestMethod.POST)
    public Object count(@RequestBody FirstFilterDto params) {
        int pageNum = params.getPageNum();
        int pageSize = params.getPageSize();
        return firstFilterService.count(params,pageNum,pageSize);
    }
}
