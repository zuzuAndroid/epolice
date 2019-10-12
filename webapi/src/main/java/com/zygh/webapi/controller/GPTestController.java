package com.zygh.webapi.controller;

import com.zygh.common.CommonResult;
import com.zygh.webapi.domain.GPTestRequest;
import com.zygh.webapi.domain.UserRegisteRequest;
import com.zygh.webapi.service.GPTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/public/db")
public class GPTestController {

    @Autowired
    GPTestService gpTestService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object get(@RequestParam String username, @RequestParam int pageNum, @RequestParam int pageSize) {

        return gpTestService.findByName(username,pageNum,pageSize);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update() {
        List<String> user = new ArrayList<>();
        user.add("test01");
        user.add("test02");
        user.add("test03");
        user.add("test04");
        user.add("test05");
        user.add("test06");
        user.add("test07");
        user.add("test08");
        user.add("test09");
        user.add("test10");

        for(;;){
            for(int i = 0;i < user.size();i++){
                gpTestService.update(user.get(i),50);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //休眠一秒钟
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(int i = 0;i < user.size();i++){
                gpTestService.release(user.get(i));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //休眠一秒钟
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}