package com.zygh.webapi.controller;


import com.zygh.webapi.service.CarColorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/car-color")
@Api(tags = "车身颜色", description = "CarColorController")
public class CarColorController {

    @Autowired
    CarColorService carColorService;

    @ApiOperation("获取车身颜色列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getList(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                          @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {
        return carColorService.findAll(pageNum,pageSize);
    }
}
