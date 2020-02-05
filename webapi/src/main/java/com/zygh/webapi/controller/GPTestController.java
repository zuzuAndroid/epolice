package com.zygh.webapi.controller;


import com.zygh.common.CommonResult;
import com.zygh.webapi.service.GPTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@RestController
@RequestMapping("/public/dbtest")
@Api(tags = "测试", description = "GPTestController")
public class GPTestController {

    @Autowired
    GPTestService gpTestService;

    // 请求总数
    public static int clientTotal = 20;

    // 同时并发执行的线程数
    public static int threadTotal = 10;

    public static int count = 0;

    List<String> user = Arrays.asList("a", "b", "c","d","e");

    @RequestMapping(value = "", method = RequestMethod.GET)
    public void test() throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();
        //信号量，此处用于控制并发的线程数
        final Semaphore semaphore = new Semaphore(threadTotal);
        //闭锁，可实现计数器递减
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(() -> {
                try {
                    //执行此方法用于获取执行许可，当总计未释放的许可数不超过200时，
                    //允许通行，否则线程阻塞等待，直到获取到许可。
                    semaphore.acquire();
                    for (int k = 0; k < user.size(); k++) {
                        int res = gpTestService.update(user.get(k), 50);
                        if(res > 0){
                            System.out.print("结果：正确");
                        }else{
                            System.out.print("结果：错误");
                        }

                        try{
                            Thread.sleep(1000);
                        }catch (Exception e){

                        }
                    }
                    //释放许可
                    semaphore.release();
                } catch (Exception e) {
                    //log.error("exception", e);
                    e.printStackTrace();
                }
                //闭锁减一
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();//线程阻塞，直到闭锁值为0时，阻塞才释放，继续往下执行
        executorService.shutdown();

    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public void update() {

            for (int i = 0; i < user.size(); i++) {
                gpTestService.release(user.get(i));
                try{
                    Thread.sleep(1000);
                }catch (Exception e){

                }
            }

    }
}
