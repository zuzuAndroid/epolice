package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zygh.webapi.dao.LogOperationMapper;
import com.zygh.webapi.pojo.LogOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogOperationService {

    @Autowired
    LogOperationMapper logOperationMapper;

    public PageInfo findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<LogOperation> list = logOperationMapper.findAll();
        PageInfo<LogOperation> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public int insert(LogOperation log){
        return logOperationMapper.insert(log);
    }
}
