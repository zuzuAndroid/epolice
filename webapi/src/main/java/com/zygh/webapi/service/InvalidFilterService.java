package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.InvalidFilterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.InvalidFilter;

import java.util.List;

@Service
public class InvalidFilterService {

    @Autowired
    InvalidFilterMapper invalidFilterMapper;

    public PageInfo findByUserName(String name, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<InvalidFilter> list = invalidFilterMapper.findByName(name);
        PageInfo<InvalidFilter> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
