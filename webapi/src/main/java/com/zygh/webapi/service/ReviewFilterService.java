package com.zygh.webapi.service;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dto.FilterCountDto;
import dto.FirstFilterDto;
import mapper.ReviewFilterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.transaction.interceptor.TransactionAspectSupport;
import pojo.FirstFilterStatistics;
import pojo.InvalidFilter;
import pojo.ReviewFilter;

import java.util.List;

@Service
public class ReviewFilterService {

    @Autowired
    ReviewFilterMapper reviewFilterMapper;

    public FilterCountDto todayCount(String username){
        String today = DateUtil.today();
        return reviewFilterMapper.todayCount(username, today);
    }

    public PageInfo findByUserName(String name, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<ReviewFilter> list = reviewFilterMapper.findByName(name);
        PageInfo<ReviewFilter> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo queryForFilter(FirstFilterDto params,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);

        List<ReviewFilter> list = reviewFilterMapper.queryForFilter(params);

        PageInfo<ReviewFilter> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public ReviewFilter prev(int id,String username){
        return reviewFilterMapper.prev(id,username);
    }

    public ReviewFilter next(int id,String username){
        return reviewFilterMapper.next(id,username);
    }

    @Async
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updatePass(int id){
        try {
            reviewFilterMapper.updatePass(id);
        }catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Async
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateInvalid(int id){
        try {
            reviewFilterMapper.updateInvalid(id);
        }catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    public int countByUsername(String username){
        return reviewFilterMapper.countByUsername(username);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public int pickUp(FirstFilterDto params){
        return reviewFilterMapper.pickUp(params);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public int release(String name){
        return reviewFilterMapper.release(name);
    }

    public PageInfo queryForSearch(FirstFilterDto params, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<ReviewFilter> list = reviewFilterMapper.queryForSearch(params);

        PageInfo<ReviewFilter> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo count(FirstFilterDto params,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<FirstFilterStatistics> list = reviewFilterMapper.count(params);

        for(int i = 0;i < list.size();i++){
            if(list.get(i).getTotal() == 0 && list.get(i).getPickupTotal() == 0){
                list.remove(i--);
            }
        }

        PageInfo<FirstFilterStatistics> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public int updateLicenseNumberById(String hphm,int id){
        return reviewFilterMapper.updateLicenseNumberById(hphm,id);
    }

    public int updateLicenseTypeById(String hpzl,int id){
        return reviewFilterMapper.updateLicenseTypeById(hpzl,id);
    }

    public ReviewFilter findById(int id){
        return reviewFilterMapper.findById(id);
    }

    public List<ReviewFilter> findByHphm(int id,String number, String dateTime){
        return reviewFilterMapper.findByHphm(id,number,dateTime);
    }

    public void releaseAll(){
        reviewFilterMapper.releaseAll();
    }

    public Integer updateDuplicate(){
        return reviewFilterMapper.updateDuplicate();
    }

    public Integer findDuplicate(){
        return reviewFilterMapper.findDuplicate();
    }

    public Integer cleanDuplicate(){
        return reviewFilterMapper.cleanDuplicate();
    }

    public Integer checkDuplicate(String dateTime){
        return reviewFilterMapper.checkDuplicate(dateTime);
    }
}
