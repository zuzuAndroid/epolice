package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.IllegalCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pojo.CarBrand;
import pojo.IllegalCode;

import java.util.List;

@Service
public class IllegalCodeService {

    @Autowired
    IllegalCodeMapper illegalCodeMapper;

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);

        List<IllegalCode> list = illegalCodeMapper.findAll();

        PageInfo<IllegalCode> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public int add(IllegalCode params){
        return illegalCodeMapper.add(params);
    }

    public int update(IllegalCode params){
        return illegalCodeMapper.update(params);
    }

    public int remove(int id){
        return illegalCodeMapper.remove(id);
    }
}
