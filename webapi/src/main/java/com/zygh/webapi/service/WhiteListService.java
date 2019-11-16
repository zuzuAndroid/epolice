package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zygh.webapi.pojo.UserDepartment;
import mapper.WhiteListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.WhiteList;

import java.util.List;

@Service
public class WhiteListService {

    @Autowired
    WhiteListMapper whiteListMapper;

    public PageInfo publicFindAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<WhiteList> list = whiteListMapper.PublicFindAll();
        PageInfo<WhiteList> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo publicFindByLicenseNumber(String number,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<WhiteList> list = whiteListMapper.PublicFindByLicenseNumber(number);
        PageInfo<WhiteList> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo privateFindAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<WhiteList> list = whiteListMapper.PrivateFindAll();
        PageInfo<WhiteList> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public int update(WhiteList params){
        return whiteListMapper.updateById(params);
    }

    public int exists(String number){
        return whiteListMapper.exists(number);
    }

    public int add(WhiteList params){
        return whiteListMapper.add(params);
    }
}
