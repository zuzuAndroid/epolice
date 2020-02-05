package com.zygh.webapi.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zygh.common.CommonResult;
import com.zygh.webapi.service.AllRoadEquipmentService;
import com.zygh.webapi.service.RoadEquipmentService;
import com.zygh.webapi.service.V2RoadEquipmentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pojo.AllRoadEquipmentView;
import pojo.RoadEquipment;
import pojo.V2RoadEquipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/road-equipment")
@Api(tags = "路口设备下拉列表(一期 + 二期)", description = "RoadEquipmentController")
public class RoadEquipmentController {

    @Autowired
    RoadEquipmentService roadEquipmentService;

    @Autowired
    V2RoadEquipmentService v2RoadEquipmentService;

    @Autowired
    AllRoadEquipmentService allRoadEquipmentService;

    @ApiOperation("获取路口设备列表(一期和二期)")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getList(@RequestParam(defaultValue = "wfcs",value = "type") String type,
                          @RequestParam(defaultValue = "",value = "startDate") String startDate,
                          @RequestParam(defaultValue = "",value = "endDate") String endDate,
                          @RequestParam(defaultValue = "",value = "wfdm") String wfdm,
                          @RequestParam(defaultValue = "0",value = "period") int period) {

        if(StrUtil.isEmpty(type)){
            return new CommonResult().validateFailed("类型不能为空");
        }

        if(StrUtil.isEmpty(startDate)){
            return new CommonResult().validateFailed("开始日期不能为空");
        }

        if(StrUtil.isEmpty(endDate)){
            return new CommonResult().validateFailed("结束日期不能为空");
        }

        return allRoadEquipmentService.findAllNoPage(type,startDate,endDate,wfdm);
    }

    @ApiOperation("获取所有路口设备")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RoadEquipment.class)
    })
    public Object getAll() {
        return allRoadEquipmentService.findAll();
    }

    @ApiOperation("搜索路口设备(一期和二期)")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Object search(@RequestBody String jsonData) {
        JSONObject jsonObject = JSON.parseObject(jsonData);

        String name = jsonObject.get("name").toString();
        String type = jsonObject.get("type").toString();
        String startDate = jsonObject.get("startDate").toString();
        String endDate = jsonObject.get("endDate").toString();
        String wfdm = jsonObject.get("wfdm").toString();

        if(StrUtil.isEmpty(name)){
            return new CommonResult().validateFailed("路口名不能为空");
        }

        if(StrUtil.isEmpty(startDate)){
            return new CommonResult().validateFailed("开始日期不能为空");
        }

        if(StrUtil.isEmpty(endDate)){
            return new CommonResult().validateFailed("结束日期不能为空");
        }

        /*List<RoadEquipment> list = new ArrayList<>();
        List<RoadEquipment> period1 = roadEquipmentService.findByNameNoPage(name);
        List<RoadEquipment> period2 = v2RoadEquipmentService.findByNameNoPage(name);
        list.addAll(period1);
        list.addAll(period2);
        Map<String, Object> data = new HashMap<>(16);
        data.put("list", list);*/

        return allRoadEquipmentService.search(name,type,startDate,endDate,wfdm);
    }

    @ApiOperation("获取路口设备和未筛选统计列表")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RoadEquipment.class)
    })
    public Object getListWithCount(@RequestParam(defaultValue = "wfcs",value = "type") String type,
                                    @RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                                   @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {

        return roadEquipmentService.findAllWithCount(type,pageNum,pageSize);
    }

    @ApiOperation("清空缓存")
    @RequestMapping(value = "/cache-clean", method = RequestMethod.POST)
    public Object clean() {
        roadEquipmentService.cacheClean();
        return new CommonResult().success();
    }
}
