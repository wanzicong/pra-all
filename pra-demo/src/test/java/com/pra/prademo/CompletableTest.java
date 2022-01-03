package com.pra.prademo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author wanzicong
 */
@SpringBootTest
public class CompletableTest {
    @Test
    void contextLoads() {
        System.out.println("test  ");
    }


    @Test
    void thread1() throws ExecutionException, InterruptedException {
        String id = "1000";
        testCompletable(id);
    }

    private void testCompletable(String doctorId) throws InterruptedException, ExecutionException {
        HashMap<String, String> result = new HashMap<>();
        // 创建 线程池 提供给Completable使用  ForkJoinPool.commonPool() 默认的
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 20, 2, TimeUnit.SECONDS, new LinkedBlockingQueue<>(20));

        // CompletableFuture 实现异步编排
        // Completable.supplyAsync()  method Completable.runAsync()  method
        CompletableFuture<Map<String, String>> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("id 是:" + doctorId);
            result.put("name", "Job");
            return result;
        }, poolExecutor);
        Map<String, String> map = future.get();
        System.out.println(map);
        // 将异步方法进行汇总 等待全部的线程完成  anyOf
        CompletableFuture.allOf(future);
    }


    @Test
    public void testSyc(){
        HashMap<String, String> lock = new HashMap<>();
        new Thread(()->{
            // 同步代码块
            synchronized(lock){
                System.out.println("同步代码块");
            }
        }).start();
    }

}
