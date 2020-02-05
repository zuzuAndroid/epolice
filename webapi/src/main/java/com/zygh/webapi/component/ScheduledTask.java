package com.zygh.webapi.component;

import cn.hutool.core.date.DateUtil;
import com.zygh.webapi.service.FirstFilterService;
import com.zygh.webapi.service.InvalidFilterService;
import com.zygh.webapi.service.ReviewFilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Set;

@Component
@Profile("prod")
public class ScheduledTask {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    FirstFilterService firstFilterService;

    @Autowired
    InvalidFilterService invalidFilterService;

    @Autowired
    ReviewFilterService reviewFilterService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 释放提取
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void releaseAll(){
        LOGGER.info("releaseAll Scheduled");
        LOGGER.info(DateUtil.now());
        firstFilterService.releaseAll();
        invalidFilterService.releaseAll();
        reviewFilterService.releaseAll();
    }

    /**
     *  同步到复核
     */
    @Scheduled(cron = "0 0 4 * * ?")
    public void copyToWffh(){
        LOGGER.info("copyToWffh Scheduled");
        LOGGER.info(DateUtil.now());
        firstFilterService.copyToWffh();
        invalidFilterService.copyToWffh();

        //更新同步状态
        firstFilterService.updateCopyToWffh();
    }

    /**
     *  去重
     */
    @Scheduled(cron = "0 0 6 * * ?")
    public void findDuplicate(){
        LOGGER.info("findDuplicate Scheduled");
        firstFilterService.findDuplicate();
        reviewFilterService.updateDuplicate();
    }


    @Scheduled(cron = "0 0 23 * * ?")
    public void redisCacheClean(){
        LOGGER.info("redisCacheClean");

        Set<String> keys = redisTemplate.keys("*");
        Iterator<String> it1 = keys.iterator();
        while (it1.hasNext()) {
            redisTemplate.delete(it1.next());
        }
    }

    /**
     * 删除已处理过的数据
    @Scheduled(cron = "0 0 6 * * ?")
    public void cleanAll(){
        firstFilterService.clean();
        invalidFilterService.clean();
        //reviewFilterService.releaseAll();
    }*/
}
