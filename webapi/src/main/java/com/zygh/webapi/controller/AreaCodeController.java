package com.zygh.webapi.controller;


import com.zygh.webapi.service.AreaCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/area-code")
@Api(tags = "区域", description = "AreaCodeController")
public class AreaCodeController {

    @Autowired
    AreaCodeService areaCodeService;

    @ApiOperation("获取区域列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getList(@RequestParam(defaultValue = "1",value = "pageNum") int pageNum,
                          @RequestParam(defaultValue = "10",value = "pageSize") int pageSize) {
        return areaCodeService.findAll(pageNum,pageSize);
    }
}
