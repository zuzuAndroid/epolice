package com.zygh.webapi.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.zygh.common.CommonResult;
import com.zygh.webapi.service.RoadEquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pojo.FirstFilterData;
import pojo.RoadEquipment;

@RestController
@RequestMapping("/public/road-equipment")
@Api(tags = "路口设备", description = "RoadEquipmentController")
public class RoadEquipmentController {

    @Autowired
    RoadEquipmentService roadEquipmentService;

    @ApiOperation("获取路口设备列表(已缓存)")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getList(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                          @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {

        return roadEquipmentService.findAll(pageNum,pageSize);
    }

    @ApiOperation("搜索路口设备")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Object search(@RequestBody JSONObject jsonObject) {

        String name = jsonObject.get("name").toString();

        if(StrUtil.isEmpty(name)){
            return new CommonResult().validateFailed("路口名不能为空");
        }

        //List<RoadEquipment> data = roadEquipmentService.findByName(name);
        return roadEquipmentService.findByName(name);
    }

    @ApiOperation("获取路口设备和未筛选统计列表")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RoadEquipment.class)
    })
    public Object getListWithCount(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                                   @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {

        return roadEquipmentService.findAllWithCount(pageNum,pageSize);
    }

    @ApiOperation("清空缓存")
    @RequestMapping(value = "/cache-clean", method = RequestMethod.POST)
    public Object clean() {
        roadEquipmentService.cacheClean();
        return new CommonResult().success();
    }
}
