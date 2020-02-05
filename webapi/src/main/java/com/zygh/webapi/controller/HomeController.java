package com.zygh.webapi.controller;

import com.zygh.webapi.aspect.NoRepeatSubmit;
import com.zygh.webapi.service.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@Api(tags = "首页", description = "HomeController")
public class HomeController {

    @Autowired
    HomeService homeService;

    @ApiOperation("初筛统计")
    @RequestMapping(value = "/total", method = RequestMethod.GET)
    @NoRepeatSubmit
    public Object queryTotal() {
        return homeService.getTotal();
    }

    @ApiOperation("今日初筛统计")
    @RequestMapping(value = "/today-total", method = RequestMethod.GET)
    public Object queryTodayTotal() {
        return homeService.getTodayTotal();
    }

    @ApiOperation("二期电警设备统计")
    @RequestMapping(value = "/v2-equipment-total", method = RequestMethod.GET)
    public Object queryV2EquipmentTotal() {
        return homeService.getV2EquipmentTotal();
    }

    @ApiOperation("二期卡口设备统计")
    @RequestMapping(value = "/v2-checkpoint-total", method = RequestMethod.GET)
    public Object queryV2CheckpointTotal() {
        return homeService.getV2CheckpointTotal();
    }

    @ApiOperation("二期违停设备统计")
    @RequestMapping(value = "/v2-parking-total", method = RequestMethod.GET)
    public Object queryV2ParkingTotal() {
        return homeService.getV2ParkingTotal();
    }

    @ApiOperation("7天数据统计")
    @RequestMapping(value = "/day-count", method = RequestMethod.GET)
    @NoRepeatSubmit
    public Object queryDay() {
        return homeService.getCountByDay();
    }

    @ApiOperation("二期违停上传7天统计")
    @RequestMapping(value = "/v2-parking-upload", method = RequestMethod.GET)
    public Object queryV2Parking() {
        return homeService.countV2ParkingUpload();
    }

    @ApiOperation("二期限行上传7天统计")
    @RequestMapping(value = "/v2-restriction-upload", method = RequestMethod.GET)
    public Object queryV2Restriction() {
        return homeService.countV2RestrictionUpload();
    }

}
