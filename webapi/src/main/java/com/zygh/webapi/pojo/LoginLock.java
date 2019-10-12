package com.zygh.webapi.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class LoginLock {

    private int id;
    private int lockType;
    private String lockContent;
    private Date dateTime;
    private boolean locked;
}
