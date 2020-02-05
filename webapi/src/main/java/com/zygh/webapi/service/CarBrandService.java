package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dto.FirstFilterDto;
import mapper.CarBrandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.CarBrand;

import java.util.List;

@Service
public class CarBrandService {

    @Autowired
    CarBrandMapper carBrandMapper;

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);

        List<CarBrand> list = carBrandMapper.findAll();

        PageInfo<CarBrand> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public int update(CarBrand params){
        return carBrandMapper.update(params);
    }

    public int add(CarBrand params){
        return carBrandMapper.add(params);
    }

    public int remove(int id){
        return carBrandMapper.remove(id);
    }
}
