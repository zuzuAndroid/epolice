package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.DirectionCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.CarBrand;
import pojo.DirectionCode;

import java.util.List;

@Service
public class DirectionCodeService {

    @Autowired
    DirectionCodeMapper directionCodeMapper;

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);

        List<DirectionCode> list = directionCodeMapper.findAll();

        PageInfo<DirectionCode> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public int update(DirectionCode params){
        return directionCodeMapper.update(params);
    }

    public int add(DirectionCode params){
        return directionCodeMapper.add(params);
    }
}
