package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.IllegalDataSourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.CarBrand;
import pojo.DirectionCode;
import pojo.IllegalDataSource;

import java.util.List;

@Service
public class IllegalDataSourceService {

    @Autowired
    IllegalDataSourceMapper illegalDataSourceMapper;

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<IllegalDataSource> list = illegalDataSourceMapper.findAll();
        PageInfo<IllegalDataSource> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public int update(IllegalDataSource illegalDataSource){
        return illegalDataSourceMapper.update(illegalDataSource);
    }

    public int add(IllegalDataSource illegalDataSource){
        return illegalDataSourceMapper.add(illegalDataSource);
    }
}
