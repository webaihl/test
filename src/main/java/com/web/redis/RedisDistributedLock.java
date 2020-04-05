package com.web.redis;

import com.web.redis.redission.RedissionManager;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author web
 * @title: RedisDistributedLock
 * @projectName javacode
 * @description: TODO
 * @date 19-10-13下午4:03
 */
@Slf4j
public class RedisDistributedLock {
    private static final String CLOSE_TASK_LOCK = "close_task_lock";
    private static final Integer lockTimeout = 5000;

    private RedissionManager redissionManager = new RedissionManager();

    public void closeTask(){
        log.info("{} 进入任务...", Thread.currentThread().getName());
        Long setnxResult = RedisSharedPoolUtil.setnx(CLOSE_TASK_LOCK,
                String.valueOf(System.currentTimeMillis()+lockTimeout));
        //锁设置成功
        if (setnxResult != null && setnxResult.intValue() == 1){
            log.info("{} 获得锁", Thread.currentThread().getName());
            close();
        }else { //继续判断，避免因为setnx后进程挂掉导致锁一直存在， 造成setnx返回null，都获取不到锁--死锁
            String lockTimeValue = RedisSharedPoolUtil.get(CLOSE_TASK_LOCK);
            //锁还存在， 并且已过期
            if (lockTimeValue != null && System.currentTimeMillis() > Long.parseLong(lockTimeValue)){
                //重新设置锁的时间，获取最新旧值
                String getsetLockTime = RedisSharedPoolUtil.getSet(CLOSE_TASK_LOCK,
                        String.valueOf(System.currentTimeMillis()+lockTimeout));
                //当key没有旧值时，即key不存在时，返回nil ->获取锁
                //获取到的锁时间和新设置的锁时间一致  存在多个tomcat，lockTimeValue可能不等于getsetLockTime
                if (getsetLockTime == null || (getsetLockTime != null && Objects.equals(lockTimeValue,getsetLockTime))){
                    log.info("{} 重新设置，获得锁", Thread.currentThread().getName());
                    close();
                }else {
                    log.info("未获取到锁: {}", Thread.currentThread().getName());
                }
            }else {
                log.info("未获取到锁: {}", Thread.currentThread().getName());
            }
        }

    }

    public void closeTaskV2(){
        RLock lock = redissionManager.getRedisson().getLock(CLOSE_TASK_LOCK);
        boolean getLock = false;
        try {
            // wait尽量为0。避免执行时间小于wait_time,导致重复拿到锁
            if (getLock = lock.tryLock(0, 5, TimeUnit.SECONDS)){
                log.info("Redission获取到分布式锁{},ThreadName: {}", CLOSE_TASK_LOCK, Thread.currentThread().getName());
            }else {
                log.info("Redission没有获取到分布式锁{},ThreadName: {}", CLOSE_TASK_LOCK, Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            log.error("Redission分布式锁获取异常", e);
        }finally {
            if (!getLock){
                return;
            }
            lock.unlock();
            log.info("Redission分布式锁释放成功！,ThreadName={}", Thread.currentThread().getName());
        }

    }

    private void close() {
        RedisSharedPoolUtil.expire(CLOSE_TASK_LOCK, lockTimeout);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            RedisSharedPoolUtil.del(CLOSE_TASK_LOCK);
            log.info("{} 释放锁成功",Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        IntStream.range(0,3).forEach(e-> new Thread(()-> new RedisDistributedLock().closeTaskV2(),String.valueOf(e)).start());
    }
}
