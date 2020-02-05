package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.V2CheckpointEquipmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.V2CheckpointEquipment;

import java.util.List;

@Service
public class V2CheckpointEquipmentService {

    @Autowired
    V2CheckpointEquipmentMapper v2CheckpointEquipmentMapper;

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<V2CheckpointEquipment> list = v2CheckpointEquipmentMapper.findAll();
        PageInfo<V2CheckpointEquipment> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public List<V2CheckpointEquipment> findAll(){
        return v2CheckpointEquipmentMapper.findAll();
    }

    public List<V2CheckpointEquipment> findByValidNoPage(){
        return v2CheckpointEquipmentMapper.findByValid();
    }

    public PageInfo findByValid(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<V2CheckpointEquipment> list = v2CheckpointEquipmentMapper.findByValid();
        PageInfo<V2CheckpointEquipment> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public List<V2CheckpointEquipment> findByName(String name){
        return v2CheckpointEquipmentMapper.findByName(name);
    }

    public List<V2CheckpointEquipment> findByCode(String code){
        return v2CheckpointEquipmentMapper.findByCode(code);
    }

    public Integer checkExist(String assetCode){
        return v2CheckpointEquipmentMapper.checkExist(assetCode);
    }

    public Integer active(int id,int valid,String remark){
        return v2CheckpointEquipmentMapper.activeEquipment(id,valid,remark);
    }

    public Integer update(V2CheckpointEquipment params){
        return v2CheckpointEquipmentMapper.update(params);
    }

    public Integer add(V2CheckpointEquipment params){
        return v2CheckpointEquipmentMapper.add(params);
    }

    public Integer validTotal(){
        return v2CheckpointEquipmentMapper.validTotal();
    }

    public Integer updateFromExcel(V2CheckpointEquipment params){
        return v2CheckpointEquipmentMapper.updateFromExcel(params);
    }

    public Integer addFromExcel(V2CheckpointEquipment params){
        return v2CheckpointEquipmentMapper.addFromExcel(params);
    }
}
