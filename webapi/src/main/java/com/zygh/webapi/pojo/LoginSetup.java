package com.zygh.webapi.pojo;

import lombok.Data;

@Data
public class LoginSetup {

    private int pwdLevel;
    private int userLock;
    private int terminalLock;
    private int userTotal;
}
