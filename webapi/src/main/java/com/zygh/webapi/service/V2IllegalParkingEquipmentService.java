package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.V2IllegalParkingEquipmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.V2IllegalParkingEquipment;

import java.util.List;

@Service
public class V2IllegalParkingEquipmentService {

    @Autowired
    V2IllegalParkingEquipmentMapper v2IllegalParkingEquipmentMapper;

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<V2IllegalParkingEquipment> list = v2IllegalParkingEquipmentMapper.findAll();
        PageInfo<V2IllegalParkingEquipment> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public List<V2IllegalParkingEquipment> findAllNoPage(){
        return v2IllegalParkingEquipmentMapper.findAll();
    }

    public List<V2IllegalParkingEquipment> findAllValidNoPage(){
        return v2IllegalParkingEquipmentMapper.findByValid();
    }

    public List<V2IllegalParkingEquipment> search(String value){
        return v2IllegalParkingEquipmentMapper.search(value);
    }

    public List<V2IllegalParkingEquipment> findByCode(String code){
        return v2IllegalParkingEquipmentMapper.findByCode(code);
    }

    public PageInfo findByValid(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<V2IllegalParkingEquipment> list = v2IllegalParkingEquipmentMapper.findByValid();
        PageInfo<V2IllegalParkingEquipment> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public Integer checkExist(String assetCode){
        return v2IllegalParkingEquipmentMapper.checkExist(assetCode);
    }

    public Integer active(int id,int valid,String remark){
        return v2IllegalParkingEquipmentMapper.activeEquipment(id,valid,remark);
    }

    public Integer update(V2IllegalParkingEquipment params){
        return v2IllegalParkingEquipmentMapper.update(params);
    }

    public Integer add(V2IllegalParkingEquipment params){
        return v2IllegalParkingEquipmentMapper.add(params);
    }

    public Integer validTotal(){
        return v2IllegalParkingEquipmentMapper.validTotal();
    }

    public Integer updateFromExcel(V2IllegalParkingEquipment params){
        return v2IllegalParkingEquipmentMapper.updateFromExcel(params);
    }

    public Integer addFromExcel(V2IllegalParkingEquipment params){
        return v2IllegalParkingEquipmentMapper.addFromExcel(params);
    }
}
