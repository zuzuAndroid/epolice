package com.zygh.webapi.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zygh.common.CommonResult;
import com.zygh.webapi.aspect.NoRepeatSubmit;
import com.zygh.webapi.service.InvalidFilterService;
import dto.FirstFilterDto;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pojo.FirstFilterData;
import pojo.InvalidFilter;

@RestController
@RequestMapping("/invalid-filter")
@Api(tags = "机筛废片", description = "InvalidFilterController")
public class InvalidFilterController {

    @Autowired
    InvalidFilterService invalidFilterService;

    @ApiOperation("统计数据")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FirstFilterData.class)
    })
    @RequestMapping(value = "/today-count", method = RequestMethod.GET)
    public Object todayCount(@RequestParam String username) {
        if(StrUtil.isEmpty(username)){
            return new CommonResult().validateFailed("username不能为空");
        }

        return invalidFilterService.todayCount(username);
    }

    @ApiOperation("根据ID获取数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id", required = true, paramType = "path"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FirstFilterData.class)
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object getById(@PathVariable(value="id") int id) {

        if(StrUtil.isEmpty(String.valueOf(id))){
            return new CommonResult().validateFailed("ID不能为空");
        }

        InvalidFilter data = invalidFilterService.findById(id);

        if(data != null){
            return data;
        }

        return new CommonResult().success(null);
    }

    @ApiOperation("获取用户已提取的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum",value = "当前页码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量", required = true, paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FirstFilterData.class)
    })
    @RequestMapping(value = "", method = RequestMethod.GET)
    @NoRepeatSubmit
    public Object get(@RequestParam String username,
                      @RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                      @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {
        return invalidFilterService.findByUserName(username,pageNum,pageSize);
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

        if(!StrUtil.isEmpty(params.getLicenseNumber()) && !Validator.isPlateNumber(params.getLicenseNumber())){
            return new CommonResult().validateFailed("车牌号码不正确");
        }

        return invalidFilterService.queryForFilter(params,pageNum,pageSize);
    }

    @ApiOperation("违法数据查询")
    @RequestMapping(value = "/query-search", method = RequestMethod.POST)
    public Object querySearch(@RequestBody FirstFilterDto params) {
        int pageNum = params.getPageNum();
        int pageSize = params.getPageSize();
        return invalidFilterService.queryForSearch(params,pageNum,pageSize);
    }

    @ApiOperation("上一条数据")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FirstFilterData.class)
    })
    @RequestMapping(value = "/prev/{id}", method = RequestMethod.GET)
    @NoRepeatSubmit
    public Object getPrev(@PathVariable(value="id") int id,@RequestParam String username) {
        InvalidFilter data = invalidFilterService.prev(id,username);

        return new CommonResult().success(data);
    }

    @ApiOperation("下一条数据")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FirstFilterData.class)
    })
    @RequestMapping(value = "/next/{id}", method = RequestMethod.GET)
    @NoRepeatSubmit
    public Object getNext(@PathVariable(value="id") int id,@RequestParam String username) {
        InvalidFilter data = invalidFilterService.next(id,username);

        return new CommonResult().success(data);
    }

    @ApiOperation("通过")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CommonResult.class)
    })
    @RequestMapping(value = "/pass/{id}",method = RequestMethod.POST)
    @NoRepeatSubmit
    public Object pass(@PathVariable(value="id") int id) {

        if(StrUtil.isEmpty(String.valueOf(id))){
            return new CommonResult().validateFailed("ID不能为空");
        }

        invalidFilterService.updatePass(id);
        //invalidFilterService.addToWffh(id);

        //if(res > 0){
            return new CommonResult().success();
        //}

        //return new CommonResult().validateFailed("通过失败");
    }

    @ApiOperation("废弃")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CommonResult.class)
    })
    @RequestMapping(value = "/invalid/{id}",method = RequestMethod.POST)
    @NoRepeatSubmit
    public Object invalid(@PathVariable(value="id") int id) {
        if(StrUtil.isEmpty(String.valueOf(id))){
            return new CommonResult().validateFailed("ID不能为空");
        }

        invalidFilterService.updateInvalid(id);

        //if(res > 0){
            return new CommonResult().success();
        //}

        //return new CommonResult().validateFailed("更新失败");
    }

    @ApiOperation("提取数据")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CommonResult.class)
    })
    @RequestMapping(value = "/pickup", method = RequestMethod.POST)
    @NoRepeatSubmit
    public Object pickup(@RequestBody FirstFilterDto params) {

        if(StrUtil.isEmpty(params.getUsername())){
            return new CommonResult().validateFailed("用户名不能为空");
        }

        if(!StrUtil.isEmpty(params.getLicenseNumber()) && !Validator.isPlateNumber(params.getLicenseNumber())){
            return new CommonResult().validateFailed("车牌号码不正确");
        }

        if(params.getLimit() > 200){
            return new CommonResult().validateFailed("提取数量错误");
        }

        int hasPickUp = invalidFilterService.countByUsername(params.getUsername());

        if(hasPickUp > 0){
            return new CommonResult().validateFailed("已提取过数据");
        }

        int res = invalidFilterService.pickUp(params);

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
    @NoRepeatSubmit
    public Object release(@RequestBody FirstFilterDto params) {
        if(StrUtil.isEmpty(params.getUsername())){
            return new CommonResult().validateFailed("用户名不能为空");
        }

        int hasPickUp = invalidFilterService.countByUsername(params.getUsername());

        if(hasPickUp == 0){
            return new CommonResult().validateFailed("数据已释放");
        }

        int res = invalidFilterService.release(params.getUsername());

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
        return invalidFilterService.count(params,pageNum,pageSize);
    }

    @ApiOperation("根据ID修改车牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "number",value = "车牌号码", required = true, paramType = "query"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CommonResult.class)
    })
    @RequestMapping(value = "/update-license-number", method = RequestMethod.POST)
    @NoRepeatSubmit
    public Object updateLicenseNumber(@RequestBody JSONObject jsonObject) {

        String number = jsonObject.get("number").toString();

        if(StrUtil.isEmpty(jsonObject.get("id").toString())){
            return new CommonResult().validateFailed("ID不能为空");
        }

        if(StrUtil.isEmpty(number)){
            return new CommonResult().validateFailed("车牌不能为空");
        }

        int id = Integer.valueOf(jsonObject.get("id").toString());

        return invalidFilterService.updateLicenseNumberById(number,id);
    }


    @ApiOperation("根据ID修改号牌种类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "hpzl",value = "号牌种类", required = true, paramType = "query"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CommonResult.class)
    })
    @RequestMapping(value = "/update-license-type", method = RequestMethod.POST)
    @NoRepeatSubmit
    public Object updateLicenseType(@RequestBody String jsonData) {
        JSONObject jsonObject = JSON.parseObject(jsonData);

        if(!jsonObject.containsKey("id") || StrUtil.isEmpty(jsonObject.get("id").toString())){
            return new CommonResult().validateFailed("ID不能为空");
        }

        if(!jsonObject.containsKey("hpzl") || StrUtil.isEmpty(jsonObject.get("hpzl").toString())){
            return new CommonResult().validateFailed("号牌种类不能为空");
        }

        int id = Integer.valueOf(jsonObject.get("id").toString());
        String hpzl = jsonObject.get("hpzl").toString();

        int res = invalidFilterService.updateLicenseTypeById(hpzl,id);

        if(res > 0){
            return new CommonResult().success();
        }

        return new CommonResult().validateFailed("修改失败");
    }


    @ApiOperation("查询相同车牌的其他违法数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hphm",value = "车牌号码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "datetime",value = "日期", required = true, paramType = "query"),
    })
    @RequestMapping(value = "/check-license-number", method = RequestMethod.GET)
    public Object checkLicenseNumber(@RequestParam int id,
                                     @RequestParam String hphm,
                                     @RequestParam String datetime) {

        if(StrUtil.isEmpty(String.valueOf(id))){
            return new CommonResult().validateFailed("id不能为空");
        }

        if(StrUtil.isEmpty(hphm)){
            return new CommonResult().validateFailed("车牌不能为空");
        }

        if(StrUtil.isEmpty(datetime)){
            return new CommonResult().validateFailed("日期不能为空");
        }

        return invalidFilterService.findByHphm(id,hphm,datetime);
    }
}
