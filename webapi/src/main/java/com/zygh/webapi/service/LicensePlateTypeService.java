package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.LicensePlateTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.LicensePlateType;

import java.util.List;

@Service
public class LicensePlateTypeService {

    @Autowired
    LicensePlateTypeMapper licensePlateTypeMapper;

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<LicensePlateType> list = licensePlateTypeMapper.findAll();

        PageInfo<LicensePlateType> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public int update(LicensePlateType params){
        return licensePlateTypeMapper.update(params);
    }

    public int add(LicensePlateType params){
        return licensePlateTypeMapper.add(params);
    }
}
