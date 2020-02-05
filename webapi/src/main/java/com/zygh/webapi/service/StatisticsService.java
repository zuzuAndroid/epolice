package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dto.FirstFilterDto;
import mapper.StatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.RoadStatistics;
import pojo.RoadStatisticsReport;
import pojo.RoadTotalStatistics;

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

    public List<RoadStatistics> v1RoadCountNoPage(FirstFilterDto params){
        return statisticsMapper.v1RoadCount(params);
    }

    public List<RoadStatistics> v2RoadCountNoPage(FirstFilterDto params){
        return statisticsMapper.v2RoadCount(params);
    }

    public PageInfo v2RoadCount(FirstFilterDto params, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<RoadStatistics> list = statisticsMapper.v2RoadCount(params);

        PageInfo<RoadStatistics> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public List<RoadTotalStatistics> roadTotalCount(FirstFilterDto params){
        return statisticsMapper.roadTotalCount(params);
    }

    public List<RoadStatisticsReport> roadReport(FirstFilterDto params){
        return statisticsMapper.roadTotalReport(params);
    }
}
