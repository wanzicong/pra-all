package com.pra.thread;

import org.springframework.scheduling.concurrent.DefaultManagedAwareThreadFactory;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author wanzicong
 */
public class ThreadDemo1 {
    public static void main(String[] args) {
        // 手动创建线程池
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                5, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20),
                new DefaultManagedAwareThreadFactory(), new ThreadPoolExecutor.DiscardOldestPolicy()
        );

        // 提交新的线程任务
        poolExecutor.submit(() -> System.out.println("执行新的任务--线程池"));

        // 关闭线程
        poolExecutor.shutdown();

        // 判断是否已经关闭
        boolean shutdown = poolExecutor.isShutdown();


        // 立即关闭线程
        List<Runnable> tasks = poolExecutor.shutdownNow();
    }
}
