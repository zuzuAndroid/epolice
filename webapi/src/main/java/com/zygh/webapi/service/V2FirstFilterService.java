package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.V2FirstFilterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.V2FirstFilter;

import java.util.List;

@Service
public class V2FirstFilterService {

    @Autowired
    V2FirstFilterMapper v2FirstFilterMapper;

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<V2FirstFilter> list = v2FirstFilterMapper.findAll();
        PageInfo<V2FirstFilter> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public V2FirstFilter prev(){
        return v2FirstFilterMapper.prev();
    }

    public V2FirstFilter next(){
        return v2FirstFilterMapper.next();
    }
}
