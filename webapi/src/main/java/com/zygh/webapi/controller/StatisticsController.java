package com.zygh.webapi.controller;

import com.zygh.webapi.service.StatisticsService;
import dto.FirstFilterDto;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/statistics")
@Api(tags = "路口统计", description = "StatisticsController")
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @RequestMapping(value = "/road", method = RequestMethod.POST)
    public Object queryFilter(@RequestBody FirstFilterDto params) {
        int pageNum = params.getPageNum();
        int pageSize = params.getPageSize();
        return statisticsService.roadCount(params,pageNum,pageSize);
    }
}
