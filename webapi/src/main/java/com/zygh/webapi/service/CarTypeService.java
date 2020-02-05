package com.zygh.webapi.service;

import mapper.CarTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.CarType;

import java.util.List;

@Service
public class CarTypeService {

    @Autowired
    CarTypeMapper carTypeMapper;

    public List<CarType> findAll(){
        return carTypeMapper.findAll();
    }
}
