package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.RestrictionWhiteListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.RestrictionWhiteList;

import java.util.List;

@Service
public class RestrictionWhiteListService {

    @Autowired
    RestrictionWhiteListMapper restrictionWhiteListMapper;

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<RestrictionWhiteList> list = restrictionWhiteListMapper.findAll();
        PageInfo<RestrictionWhiteList> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo findByLicenseNumber(String number,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<RestrictionWhiteList> list = restrictionWhiteListMapper.findByLicenseNumber(number);
        PageInfo<RestrictionWhiteList> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public int exists(String number){
        return restrictionWhiteListMapper.exists(number);
    }

    public int add(RestrictionWhiteList params){
        return restrictionWhiteListMapper.add(params);
    }
}
