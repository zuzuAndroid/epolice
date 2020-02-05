package com.zygh.webapi.service;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dto.FilterCountDto;
import dto.FirstFilterDto;
import mapper.FirstFilterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import pojo.FirstFilterData;
import pojo.FirstFilterStatistics;

import java.util.List;

@Service
public class FirstFilterService {

    @Autowired
    FirstFilterMapper firstFilterMapper;


    public FilterCountDto todayCount(String username){
        String today = DateUtil.today();
        return firstFilterMapper.todayCount(username,today);
    }

    public PageInfo findByUserName(String name,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<FirstFilterData> list = firstFilterMapper.findByName(name);
        PageInfo<FirstFilterData> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo queryForFilter(FirstFilterDto params,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);

        List<FirstFilterData> list = firstFilterMapper.queryForFilter(params);

        PageInfo<FirstFilterData> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public FirstFilterData prev(int id,String username){
        return firstFilterMapper.prev(id,username);
    }

    public FirstFilterData next(int id,String username){
        return firstFilterMapper.next(id,username);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Async
    public void updatePass(int id){
        try {
            firstFilterMapper.updatePass(id);
        }catch (Exception e) {
            System.out.println(e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            //return -1;
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public int addToWffh(int id){
        return firstFilterMapper.addWffh(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Async
    public void updateInvalid(int id){
        try {
            firstFilterMapper.updateInvalid(id);
        }catch (Exception e) {
            System.out.println(e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            //return -1;
        }
    }

    public int countByUsername(String username){
        return firstFilterMapper.countByUsername(username);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public int pickUp(FirstFilterDto params){
        try {
            return firstFilterMapper.pickUp(params);
        }catch (Exception e){
            System.out.print(e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return -1;
        }
    }

    @Async
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void release(String name){
        try {
            firstFilterMapper.release(name);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            //return -1;
        }
    }

    public PageInfo queryForSearch(FirstFilterDto params,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<FirstFilterData> list = firstFilterMapper.queryForSearch(params);

        PageInfo<FirstFilterData> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo count(FirstFilterDto params,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<FirstFilterStatistics> list = firstFilterMapper.count(params);

        PageInfo<FirstFilterStatistics> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public int updateLicenseNumberById(String hphm,int id){
        return firstFilterMapper.updateLicenseNumberById(hphm,id);
    }

    public int updateLicenseTypeById(String hpzl,int id){
        return firstFilterMapper.updateLicenseTypeById(hpzl,id);
    }

    public FirstFilterData findById(int id){
        return firstFilterMapper.findById(id);
    }

    public List<FirstFilterData> findByHphm(int id,String number,String dateTime){
        return firstFilterMapper.findByHphm(id,number,dateTime);
    }

    public void releaseAll(){
        firstFilterMapper.releaseAll();
    }

    public Integer clean(){
        return firstFilterMapper.clean();
    }

    public Integer copyToWffh(){
        return firstFilterMapper.copyToWffh();
    }

    public Integer updateCopyToWffh(){
        return firstFilterMapper.updateCopyToWffh();
    }

    public List<FirstFilterData> findToWffh(String startDate,String endDate){
        return firstFilterMapper.findToWffh(startDate,endDate);
    }

    public Integer updateToWffh(String startDate,String endDate){
        return firstFilterMapper.updateToWffh(startDate,endDate);
    }

    public Integer copyToWffhByManual(String startDate,String endDate){
        return firstFilterMapper.copyToWffhByManual(startDate,endDate);
    }

    public void findDuplicate(){
        firstFilterMapper.findDuplicate();
    }

    public Integer checkDuplicate(String dateTime){
        return firstFilterMapper.checkDuplicate(dateTime);
    }

}
