package com.zygh.webapi.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    //每天6点触发
    @Scheduled(cron = "0 0 6 * * ?")
    public void releaseAll(){
        LOGGER.info("scheduled");
    }
}
