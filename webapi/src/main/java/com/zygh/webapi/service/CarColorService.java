package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.CarColorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.CarColor;

import java.util.List;

@Service
public class CarColorService {

    @Autowired
    CarColorMapper carColorMapper;

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);

        List<CarColor> list = carColorMapper.findAll();

        PageInfo<CarColor> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
