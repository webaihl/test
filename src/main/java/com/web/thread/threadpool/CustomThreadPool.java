package com.web.thread.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName CustomThreadPool.java
 * @Description
 * @createTime 2020年03月22日 21:14:00
 */
@Slf4j
public class CustomThreadPool {
    private volatile int coreSize;
    private volatile int maxSize;
    private long keepAliveTime;
    private TimeUnit timeUnit;
    //存放所有线程的阻塞队列
    private BlockingQueue<Runnable> workQueue;

    //存放线程池中用于执行的线程
    private Set<Worker> workers;

    private Notify notify;

    //线程池中任务的总数
    private AtomicInteger totalTask = new AtomicInteger();

    //是否关闭线程池标志
    private AtomicBoolean isShutDown = new AtomicBoolean(false);

    //线程池任务全部执行完毕后的通知组件
    private final Object shutDownNotify = new Object();

    //获取队列任务的锁
    private final ReentrantLock lock = new ReentrantLock();

    public CustomThreadPool(int coreSize, int maxSize, long keepAliveTime, TimeUnit timeUnit,
                            BlockingQueue<Runnable> workQueue, Notify notify) {
        this.coreSize = coreSize;
        this.maxSize = maxSize;
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
        this.notify = notify;
        this.workQueue = workQueue;

        workers = new ConcurrentHashSet<>();
    }

    /**
     * 有返回值
     *
     * @param callable
     * @param <T>
     * @return
     */
    public <T> Future<T> submit(Callable<T> callable) {
        FutureTask<T> future = new FutureTask(callable);
        execute(future);
        return future;
    }

    /**
     * 执行添加的任务
     *
     * @param runnable 需要执行的任务
     */
    public void execute(Runnable runnable) {

        if (runnable == null) {
            throw new NullPointerException("runnable nullPointerException");
        }

        if (isShutDown.get()) {
            log.info("线程池已经关闭，不能再提交任务！");
            return;
            //throw new IllegalStateException("线程池已经关闭！");
        }

        //开始处理任务
        totalTask.incrementAndGet();
        // 如果执行的线程数量少于核心数量，新建线程
        if (workers.size() < coreSize) {
            addWorker(runnable);
            return;
        }

        //核心线程数已满， 存放到队列中
        boolean offer = workQueue.offer(runnable);
        //写入队列失败
        if (!offer) {
            //判断是否达到最大线程数
            if (workers.size() < maxSize) {
                addWorker(runnable);
                return;
            } else {
                log.error("超过最大线程数");
                //尝试放入队列，会阻塞。 (j.u.c 会在这里执行拒绝策略)
                try {
                    workQueue.put(runnable);
                } catch (InterruptedException ignored) {
                    ignored.printStackTrace();
                }
            }
        }
    }

    /**
     * 添加任务到队列，需要加锁
     *
     * @param runnable
     */
    private void addWorker(Runnable runnable) {
        Worker worker = new Worker(runnable, true);
        worker.startTask();
        workers.add(worker);
    }

    /**
     *
     */
    private final class Worker extends Thread {

        // 需要执行的任务
        private Runnable task;
        //新建任务(true)还是从队列获取(false)
        private boolean isNewTask;

        private Thread thread;

        public Worker(Runnable task, boolean isNewTask) {
            this.task = task;
            this.isNewTask = isNewTask;
            thread = this;
        }


        public void startTask() {
            //执行worker线程本身
            thread.start();
        }

        public void stopTask() {
            thread.interrupt();
        }

        @Override
        public void run() {

            Runnable task = null;

            if (isNewTask) {
                task = this.task;
            }

            //任务是否执行完成
            boolean complete = true;
            try {
                while (task != null || (task = getTask()) != null) {
                    try {
                        task.run();
                    } catch (Exception e) {
                        complete = false;
                        throw e;
                    } finally {
                        //任务被执行后的处理
                        task = null;
                        int number = totalTask.decrementAndGet();
                        if (number == 0) {
                            synchronized (shutDownNotify) {
                                shutDownNotify.notify();
                            }
                        }
                    }
                }
            } finally {
                //获取不到线程时，释放自己
                boolean remove = workers.remove(this);
                if (!complete) {
                    addWorker(null);
                }

                // 尝试关闭线程池
                tryClose(true);

            }
        }
    }

    /**
     * 从队列中获取任务
     *
     * @return
     */
    private Runnable getTask() {
        if (isShutDown.get() && totalTask.get() == 0) {
            return null;
        }

        // 不加锁会导致 workers.size() > miniSize 条件多次执行，从而导致线程被全部回收完毕。
        lock.lock();
        try {
            Runnable task = null;
            if (workers.size() > coreSize) {
                //大于核心线程数时需要用保活时间获取任务
                task = workQueue.poll(keepAliveTime, timeUnit);
            } else {
                // 不关闭线程池的时候， 会有coreSize个线程阻塞
                task = workQueue.take();
            }
            if (task != null) {
                return task;
            }
        } catch (InterruptedException e) {
            return null;
        } finally {
            lock.unlock();
        }
        return null;
    }

    /**
     * 立即关闭线程池
     */
    public void shutDownNow() {
        isShutDown.set(true);
        tryClose(false);
    }

    /**
     * 安全关闭线程池
     */
    public void shutDown() {
        isShutDown.set(true);
        tryClose(true);
    }

    /**
     * 关闭操作
     *
     * @param isTry true  尝试关闭     --> 会等待所有任务执行完毕
     *              false 立即关闭     --> 任务有丢失的可能
     */
    private void tryClose(boolean isTry) {
        //立即关闭
        if (!isTry) {
            closeAllTask();
        } else {
            if (isShutDown.get() && totalTask.get() == 0) {
                closeAllTask();
            }
        }
    }

    /**
     * 关闭所有任务
     */
    private void closeAllTask() {
        for (Worker worker : workers) {
            worker.stopTask();
        }
    }

    /**
     * 获取工作线程数量
     *
     * @return
     */
    public int getWorkerCount() {
        return workers.size();
    }

    /**
     * 阻塞等到任务执行完毕
     */
    public void mainNotify() {
        synchronized (shutDownNotify) {
            while (totalTask.get() > 0) {
                try {
                    shutDownNotify.wait();
                    if (notify != null) {
                        notify.notifyListen();
                    }
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    /**
     * 存放工作线程 线程安全
     *
     * @param <T>
     */
    private final class ConcurrentHashSet<T> extends AbstractSet<T> {
        private ConcurrentHashMap<T, Object> map = new ConcurrentHashMap<>();
        private final Object PRESENT = new Object();
        // 因为ConcurrentHashMap的size不准确， 所以用count字段代替
        private AtomicInteger count = new AtomicInteger();

        @Override
        public Iterator<T> iterator() {
            return map.keySet().iterator();
        }

        @Override
        public int size() {
            return count.get();
        }

        @Override
        public boolean add(T t) {
            count.incrementAndGet();
            return map.put(t, PRESENT) == null;
        }

        @Override
        public boolean remove(Object o) {
            count.decrementAndGet();
            return map.remove(o) == PRESENT;
        }
    }
}

