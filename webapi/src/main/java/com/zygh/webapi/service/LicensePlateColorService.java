package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.LicensePlateColorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pojo.LicensePlateColor;
import pojo.LicensePlateType;

import java.util.List;

@Service
public class LicensePlateColorService {

    @Autowired
    LicensePlateColorMapper licensePlateColorMapper;

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);

        List<LicensePlateColor> list = licensePlateColorMapper.findAll();

        PageInfo<LicensePlateColor> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public int update(LicensePlateColor params){
        return licensePlateColorMapper.update(params);
    }

    public int add(LicensePlateColor params){
        return licensePlateColorMapper.add(params);
    }
}
