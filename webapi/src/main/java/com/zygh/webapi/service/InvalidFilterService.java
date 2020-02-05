package com.zygh.webapi.service;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dto.FilterCountDto;
import dto.FirstFilterDto;
import mapper.InvalidFilterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import pojo.FirstFilterData;
import pojo.FirstFilterStatistics;
import pojo.InvalidFilter;

import java.util.List;

@Service
public class InvalidFilterService {

    @Autowired
    InvalidFilterMapper invalidFilterMapper;

    public FilterCountDto todayCount(String username){
        String today = DateUtil.today();
        return invalidFilterMapper.todayCount(username,today);
    }

    public PageInfo findByUserName(String name,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<InvalidFilter> list = invalidFilterMapper.findByName(name);
        PageInfo<InvalidFilter> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo queryForFilter(FirstFilterDto params,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);

        List<InvalidFilter> list = invalidFilterMapper.queryForFilter(params);

        PageInfo<InvalidFilter> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public InvalidFilter prev(int id,String username){
        return invalidFilterMapper.prev(id,username);
    }

    public InvalidFilter next(int id,String username){
        return invalidFilterMapper.next(id,username);
    }

    @Async
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updatePass(int id){
        try {
            invalidFilterMapper.updatePass(id);
        }catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public int addToWffh(int id){
        return invalidFilterMapper.addWffh(id);
    }

    @Async
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateInvalid(int id){
        try {
            invalidFilterMapper.updateInvalid(id);
        }catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    public int countByUsername(String username){
        return invalidFilterMapper.countByUsername(username);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public int pickUp(FirstFilterDto params){
        try {
            return invalidFilterMapper.pickUp(params);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public int release(String name){
        return invalidFilterMapper.release(name);
    }

    public PageInfo queryForSearch(FirstFilterDto params,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<InvalidFilter> list = invalidFilterMapper.queryForSearch(params);

        PageInfo<InvalidFilter> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo count(FirstFilterDto params, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<FirstFilterStatistics> list = invalidFilterMapper.count(params);

        PageInfo<FirstFilterStatistics> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public int updateLicenseNumberById(String hphm,int id){
        return invalidFilterMapper.updateLicenseNumberById(hphm,id);
    }

    public int updateLicenseTypeById(String hpzl,int id){
        return invalidFilterMapper.updateLicenseTypeById(hpzl,id);
    }

    public InvalidFilter findById(int id){
        return invalidFilterMapper.findById(id);
    }

    public List<InvalidFilter> findByHphm(int id,String number,String dateTime){
        return invalidFilterMapper.findByHphm(id,number,dateTime);
    }

    public void releaseAll(){
        invalidFilterMapper.releaseAll();
    }

    public Integer clean(){
        return invalidFilterMapper.clean();
    }

    public Integer copyToWffh(){
        return invalidFilterMapper.copyToWffh();
    }
}
