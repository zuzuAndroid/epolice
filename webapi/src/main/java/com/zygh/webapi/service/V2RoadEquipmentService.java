package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.V2RoadEquipmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pojo.RoadEquipment;
import pojo.V2CheckpointEquipment;
import pojo.V2RoadEquipment;

import java.util.List;

@Service
public class V2RoadEquipmentService {

    @Autowired
    V2RoadEquipmentMapper v2RoadEquipmentMapper;

    public List<RoadEquipment> findAllNoPage(String type,String startDate,String endDate){
        if(type.equals("wfcs")){
            return v2RoadEquipmentMapper.findAllWithCountForWfcs();
        }else if(type.equals("jsfp")){
            return v2RoadEquipmentMapper.findAllWithCountForJsfp();
        }
        return v2RoadEquipmentMapper.findAllWithCountForWffh();
    }

    public List<V2RoadEquipment> findAllNoPage(){
        return v2RoadEquipmentMapper.findAll();
    }

    public List<V2RoadEquipment> findByValidNoPage(){
        return v2RoadEquipmentMapper.findByValid();
    }

    public PageInfo findByValid(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<V2RoadEquipment> list = v2RoadEquipmentMapper.findByValid();
        PageInfo<V2RoadEquipment> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public List<V2RoadEquipment> search(String value){
        return v2RoadEquipmentMapper.search(value);
    }

    public List<V2RoadEquipment> findByCode(String code){
        return v2RoadEquipmentMapper.findByCode(code);
    }

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<V2RoadEquipment> list = v2RoadEquipmentMapper.findAll();
        PageInfo<V2RoadEquipment> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo findByCompany(String name,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<V2RoadEquipment> list = v2RoadEquipmentMapper.findByCompany(name);
        PageInfo<V2RoadEquipment> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo findByName(String name,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<RoadEquipment> list = v2RoadEquipmentMapper.findByName(name);
        PageInfo<RoadEquipment> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public List<RoadEquipment> findByNameNoPage(String name){
        return v2RoadEquipmentMapper.findByName(name);
    }

    /*
    public PageInfo findAllWithCount(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<RoadEquipment> list = v2RoadEquipmentMapper.findAllWithCount();

        PageInfo<RoadEquipment> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }*/

    public Integer checkExist(String assetNo){
        return v2RoadEquipmentMapper.checkExist(assetNo);
    }

    public Integer update(V2RoadEquipment params){
        return v2RoadEquipmentMapper.update(params);
    }

    public Integer add(V2RoadEquipment params){
        return v2RoadEquipmentMapper.add(params);
    }

    public Integer active(int id,int valid,String remark){
        return v2RoadEquipmentMapper.activeEquipment(id,valid,remark);
    }

    public Integer validTotal(){
        return v2RoadEquipmentMapper.validTotal();
    }

    public Integer updateFromExcel(V2RoadEquipment params){
        return v2RoadEquipmentMapper.updateFromExcel(params);
    }

    public Integer addFromExcel(V2RoadEquipment params){
        return v2RoadEquipmentMapper.addFromExcel(params);
    }
}
