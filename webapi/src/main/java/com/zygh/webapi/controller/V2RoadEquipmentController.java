package com.zygh.webapi.controller;


import com.zygh.webapi.service.V2RoadEquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pojo.V2RoadEquipment;

@RestController
@RequestMapping("/public/v2-road-equipment")
@Api(tags = "二期路面设备", description = "V2RoadEquipmentController")
public class V2RoadEquipmentController {

    @Autowired
    V2RoadEquipmentService v2RoadEquipmentService;

    @ApiOperation("获取数据")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = V2RoadEquipment.class)
    })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object get(@RequestParam int pageNum, @RequestParam int pageSize) {
        return v2RoadEquipmentService.findAll(pageNum,pageSize);
    }


}
