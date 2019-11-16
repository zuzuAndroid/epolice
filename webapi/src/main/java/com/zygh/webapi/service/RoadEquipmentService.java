package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.RoadEquipmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pojo.RoadEquipment;

import java.util.List;

@Service
@CacheConfig(cacheNames = "roadEquipment")
public class RoadEquipmentService {

    @Autowired
    RoadEquipmentMapper roadEquipmentMapper;

    @Cacheable(value="roadEquipmentList", key="#pageNum")
    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<RoadEquipment> list = roadEquipmentMapper.findAll();

        PageInfo<RoadEquipment> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public List<RoadEquipment> findByName(String name){
        return roadEquipmentMapper.findByName(name);
    }

    public PageInfo findAllWithCount(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<RoadEquipment> list = roadEquipmentMapper.findAllWithCount();

        PageInfo<RoadEquipment> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @CacheEvict(cacheNames = "roadEquipment", allEntries=true, beforeInvocation=true)
    public void cacheClean(){

    }
}
