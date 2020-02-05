package com.zygh.webapi.service;

import mapper.HomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import pojo.Home;

import java.util.List;

@Service
public class HomeService {

    @Autowired
    HomeMapper homeMapper;

    @Autowired
    RedisTemplate redisTemplate;

    public Home getTotal(){
        return homeMapper.countTotal();
    }

    public Home getTodayTotal(){
        return homeMapper.todayCountTotal();
    }

    @Cacheable(value="HomeService", key="'v2-equipment-total'")
    public Home getV2EquipmentTotal(){
        return homeMapper.countV2Equipment();
    }

    @Cacheable(value="HomeService", key="'v2-checkpoint-total'")
    public Home getV2CheckpointTotal(){
        return homeMapper.countV2Checkpoint();
    }

    @Cacheable(value="HomeService", key="'v2-parking-total'")
    public Home getV2ParkingTotal(){
        return homeMapper.countV2Parking();
    }

    @Cacheable(value="HomeService", key="'count-by-day'")
    public List<Home> getCountByDay(){
        return homeMapper.countByDay();
    }

    @Cacheable(value="HomeService", key="'count-v2-parking'")
    public List<Home> countV2ParkingUpload(){
        return homeMapper.countV2ParkingUpload();
    }

    @Cacheable(value="HomeService", key="'count-v2-restriction'")
    public List<Home> countV2RestrictionUpload(){
        return homeMapper.countV2RestrictionUpload();
    }

    @CacheEvict(value="HomeService",allEntries=true)
    public void cleanCache(){

    }
}
