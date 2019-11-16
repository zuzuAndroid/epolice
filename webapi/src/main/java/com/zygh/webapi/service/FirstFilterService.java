package com.zygh.webapi.service;


import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dto.FirstFilterDto;
import mapper.FirstFilterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojo.FirstFilterData;
import pojo.FirstFilterStatistics;

import java.util.List;

@Service
public class FirstFilterService {

    @Autowired
    FirstFilterMapper firstFilterMapper;

    public PageInfo findByUserName(String name,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<FirstFilterData> list = firstFilterMapper.findByName(name);
        PageInfo<FirstFilterData> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo queryForFilter(FirstFilterDto params,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);

        List<FirstFilterData> list = firstFilterMapper.queryForFilter(params);

        PageInfo<FirstFilterData> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public FirstFilterData prev(int id,String username){
        return firstFilterMapper.prev(id,username);
    }

    public FirstFilterData next(int id,String username){
        return firstFilterMapper.next(id,username);
    }

    @Transactional
    public int updatePass(int id){
        return firstFilterMapper.updatePass(id);
    }

    public int addToWffh(int id){
        return firstFilterMapper.addWffh(id);
    }

    @Transactional
    public int updateInvalid(int id){
        return firstFilterMapper.updateInvalid(id);
    }

    @Transactional
    public int pickUp(FirstFilterDto params){
        return firstFilterMapper.pickUp(params);
    }

    @Transactional
    public int release(String name){
        return firstFilterMapper.release(name);
    }

    public PageInfo queryForSearch(FirstFilterDto params,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<FirstFilterData> list = firstFilterMapper.queryForSearch(params);

        PageInfo<FirstFilterData> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo count(FirstFilterDto params,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<FirstFilterStatistics> list = firstFilterMapper.count(params.getIllegalCode(),params.getStartDate(),params.getEndDate());

        PageInfo<FirstFilterStatistics> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
