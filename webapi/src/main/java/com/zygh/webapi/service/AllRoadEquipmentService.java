package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.AllRoadEquipmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.AllRoadEquipmentView;

import java.util.List;

@Service
public class AllRoadEquipmentService {

    @Autowired
    AllRoadEquipmentMapper allRoadEquipmentMapper;

    public List<AllRoadEquipmentView> findAll(){
        return allRoadEquipmentMapper.findAll();
    }

    public List<AllRoadEquipmentView> findAllNoPage(String type,
                                                    String startDate,
                                                    String endDate,
                                                    String wfdm){
        if(type.equals("wfcs")){
            return allRoadEquipmentMapper.findAllWithCountForWfcs(startDate,endDate,wfdm);
        }else if(type.equals("jsfp")){
            return allRoadEquipmentMapper.findAllWithCountForJsfp(startDate,endDate,wfdm);
        }else{
            return allRoadEquipmentMapper.findAllWithCountForWffh(startDate,endDate,wfdm);
        }
    }

    public PageInfo findByPeriod(int period,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<AllRoadEquipmentView> list = allRoadEquipmentMapper.findByPeriod(period);

        PageInfo<AllRoadEquipmentView> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public List<AllRoadEquipmentView> search(String name,
                                             String type,
                                             String startDate,
                                             String endDate,
                                             String wfdm){
        if(type.equals("wfcs")) {
            return allRoadEquipmentMapper.searchForWfcs(name,startDate,endDate,wfdm);
        }else if(type.equals("jsfp")){
            return allRoadEquipmentMapper.searchForJsfp(name,startDate,endDate,wfdm);
        }else{
            return allRoadEquipmentMapper.searchForWffh(name,startDate,endDate,wfdm);
        }
    }
}
