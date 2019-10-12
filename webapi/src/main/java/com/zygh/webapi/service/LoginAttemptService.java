package com.zygh.webapi.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.zygh.webapi.pojo.LoginSetup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private int userLockNumber = 0;
    private int terminalLockNumber = 0;

    private LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptService() {
        super();
        attemptsCache = CacheBuilder.newBuilder()
                        .expireAfterWrite(1, TimeUnit.DAYS)
                        .build(new CacheLoader<String, Integer>() {
                            public Integer load(String key) {
                                return 0;
                            }
                        });
    }

    public void setUserLockNumber(int number){
        userLockNumber = number;
    }

    public void setTerminalLockNumber(int number){
        terminalLockNumber = number;
    }

    public int getUserLockNumber(){
        return userLockNumber;
    }

    public int getTerminalLockNumber(){
        return terminalLockNumber;
    }

    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key);
    }

    public void loginFailed(String key) {
        LOGGER.info(key);
        int attempts = 0;
        try {
            attempts = attemptsCache.get(key);
        } catch (ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(key, attempts);
    }

    public int getRestNumber(String key){
        try {
            return attemptsCache.get(key);
        }catch (ExecutionException e) {
            return -1;
        }
    }

    public boolean isBlockedByName(String key) {
        try {
            return attemptsCache.get(key) >= userLockNumber;
        } catch (ExecutionException e) {
            return false;
        }
    }

    public boolean isBlockedByIp(String key) {
        try {
            return attemptsCache.get(key) >= terminalLockNumber;
        } catch (ExecutionException e) {
            return false;
        }
    }
}
