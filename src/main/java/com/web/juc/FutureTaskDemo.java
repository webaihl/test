package com.web.juc;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class FutureTaskDemo implements Callable<String> {

    public FutureTaskDemo(String acceptStr) {
        this.acceptStr = acceptStr;
    }

    private String acceptStr;

    @Override
    public String call() throws Exception {
        // 任务阻塞 1 秒
        TimeUnit.SECONDS.sleep(5);
        return this.acceptStr + " append some chars and return it!";
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {

       /* ExecutorService service = Executors.newCachedThreadPool();
        Future<String> res = service.submit(new FutureTaskDemo("web"));
        log.info("result:  {}", res.get());*/

        Callable<String> callable = new FutureTaskDemo("my callable test!");
        FutureTask<String> task = new FutureTask<>(callable);
        long beginTime = System.currentTimeMillis();
        // 创建线程
        new Thread(task).start();
        // 调用get()阻塞主线程，反之，线程不会阻塞
        log.info("main thread do sth");
        String result = task.get();
        long endTime = System.currentTimeMillis();
        log.info("hello : {}" , result);
        log.info("cast : " + (endTime - beginTime) / 1000 + " second!");
    }
}
