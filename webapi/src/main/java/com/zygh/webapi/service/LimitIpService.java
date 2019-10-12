package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zygh.webapi.dao.LimitIpMapper;
import com.zygh.webapi.pojo.LimitIp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LimitIpService {

    @Autowired
    LimitIpMapper limitIpMapper;

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<LimitIp> list = limitIpMapper.findAll();
        PageInfo<LimitIp> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Transactional
    public int insert(String ip){
        return limitIpMapper.insert(ip);
    }

    @Transactional
    public int update(LimitIp ip){
        return limitIpMapper.update(ip);
    }
}
