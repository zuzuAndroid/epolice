package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.AreaCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.AreaCode;

import java.util.List;

@Service
public class AreaCodeService {

    @Autowired
    AreaCodeMapper areaCodeMapper;

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);

        List<AreaCode> list = areaCodeMapper.findAll();

        PageInfo<AreaCode> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
