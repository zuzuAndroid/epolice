package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.V2RoadEquipmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.V2RoadEquipment;

import java.util.List;

@Service
public class V2RoadEquipmentService {

    @Autowired
    V2RoadEquipmentMapper v2RoadEquipmentMapper;

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

    public int update(V2RoadEquipment params){
        return v2RoadEquipmentMapper.update(params);
    }
}
