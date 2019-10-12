package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zygh.webapi.dao.GPTestMapper;
import com.zygh.webapi.pojo.GPTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GPTestService {

    @Autowired
    GPTestMapper gpTestMapper;

    public PageInfo findByName(String username,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<GPTest> list = gpTestMapper.findByName(username);
        PageInfo<GPTest> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Integer update(String username,int pageSize){
        return gpTestMapper.update(username,pageSize);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Integer release(String username){
        return gpTestMapper.release(username);
    }
}
