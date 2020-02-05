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
public class RoadEquipmentService {

    @Autowired
    RoadEquipmentMapper roadEquipmentMapper;

    //@Cacheable(value="roadEquipmentList", key="#type")
    public List<RoadEquipment> findAllNoPage(String type){
        if(type.equals("wfcs")){
            return roadEquipmentMapper.findAllWithCountForWfcs();
        }else if(type.equals("jsfp")){
            return roadEquipmentMapper.findAllWithCountForJsfp();
        }else{
            return roadEquipmentMapper.findAllWithCountForWffh();
        }
    }

    //@Cacheable(value="roadEquipmentList", key="#pageNum")
    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<RoadEquipment> list = roadEquipmentMapper.findAll();

        PageInfo<RoadEquipment> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public List<RoadEquipment> findByNameNoPage(String name){
        return roadEquipmentMapper.findByName(name);
    }


    public PageInfo findAllWithCount(String type,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<RoadEquipment> list;

        if(type.equals("wfcs")){
            list = roadEquipmentMapper.findAllWithCountForWfcs();
        }else if(type.equals("jsfp")){
            list = roadEquipmentMapper.findAllWithCountForJsfp();
        }else{
            list = roadEquipmentMapper.findAllWithCountForWffh();
        }

        PageInfo<RoadEquipment> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    //@CacheEvict(cacheNames = "roadEquipment", allEntries=true, beforeInvocation=true)
    public void cacheClean(){

    }
}
