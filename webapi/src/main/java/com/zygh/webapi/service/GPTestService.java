package com.zygh.webapi.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zygh.webapi.dao.GPTestMapper;
import com.zygh.webapi.pojo.GPTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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

    @Transactional
    public Integer update(String username,int pageSize){
        try {
            return gpTestMapper.update(username,pageSize);
        }catch (Exception e){
            System.out.print("结果：错误"+e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return -1;
        }

    }


    public Integer release(String username){
        return gpTestMapper.release(username);
    }
}
