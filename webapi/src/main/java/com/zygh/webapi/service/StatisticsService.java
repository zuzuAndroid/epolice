package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dto.FirstFilterDto;
import mapper.StatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.RoadStatistics;

import java.util.List;

@Service
public class StatisticsService {

    @Autowired
    StatisticsMapper statisticsMapper;

    public PageInfo roadCount(FirstFilterDto params, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<RoadStatistics> list = statisticsMapper.roadCount(params);

        PageInfo<RoadStatistics> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
