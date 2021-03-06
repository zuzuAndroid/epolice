package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.HxWhiteListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.HxWhiteList;
import pojo.RestrictionWhiteList;
import pojo.WhiteList;

import java.util.List;

@Service
public class HxWhiteListService {

    @Autowired
    HxWhiteListMapper hxWhiteListMapper;

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<HxWhiteList> list = hxWhiteListMapper.findAll();
        PageInfo<HxWhiteList> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo findByLicenseNumber(String number,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<HxWhiteList> list = hxWhiteListMapper.findByLicenseNumber(number);
        PageInfo<HxWhiteList> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public int exists(String number){
        return hxWhiteListMapper.exists(number);
    }

    public int add(HxWhiteList params){
        return hxWhiteListMapper.add(params);
    }

    public Integer update(HxWhiteList params){
        return hxWhiteListMapper.update(params);
    }

    public Integer remove(int id){
        return hxWhiteListMapper.remove(id);
    }
}
