package com.web.thread.threadpool;
/**
 * 线程池的线程数量达corePoolSize后，即使线程池没有可执行任务时，也不会释放线程。
 * FixedThreadPool的工作队列为无界队列LinkedBlockingQueue(队列容量为Integer.MAX_VALUE), 这会导致以下问题:
 * - 线程池里的线程数量不超过corePoolSize,这导致了maximumPoolSize和keepAliveTime将会是个无用参数
 * - 由于使用了无界队列, 所以FixedThreadPool永远不会拒绝, 即饱和策略失效
 * <p>
 * 初始化的线程池中只有一个线程，如果该线程异常结束，会重新创建一个新的线程继续执行任务，唯一的线程可以保证所提交任务的顺序执行.
 * 由于使用了无界队列, 所以SingleThreadPool永远不会拒绝, 即饱和策略失效
 * <p>
 * 线程池的线程数可达到Integer.MAX_VALUE，即2147483647，内部使用SynchronousQueue作为阻塞队列；
 * 和newFixedThreadPool创建的线程池不同，newCachedThreadPool在没有任务执行时，当线程的空闲时间超过keepAliveTime，会自动释放线程资源，
 * 当提交新任务时，如果没有空闲线程，则创建新线程执行任务，会导致一定的系统开销；
 */

/**
 * 初始化的线程池中只有一个线程，如果该线程异常结束，会重新创建一个新的线程继续执行任务，唯一的线程可以保证所提交任务的顺序执行.
 由于使用了无界队列, 所以SingleThreadPool永远不会拒绝, 即饱和策略失效
 */
/**
 * 线程池的线程数可达到Integer.MAX_VALUE，即2147483647，内部使用SynchronousQueue作为阻塞队列；
 和newFixedThreadPool创建的线程池不同，newCachedThreadPool在没有任务执行时，当线程的空闲时间超过keepAliveTime，会自动释放线程资源，
 当提交新任务时，如果没有空闲线程，则创建新线程执行任务，会导致一定的系统开销；
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolDemo {

    public static void main(String[] args) {

        /**
         * Callable()处理结果
         * Future()获取结果
         */
        System.out.println("----");
        ExecutorService eService = /*Executors.newFixedThreadPool(4);*/
                /*Executors.newSingleThreadExecutor(); */
                Executors.newCachedThreadPool();
		/*Future<String> task = eService.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				Thread.sleep(3000);
				return "I am a task\n\n"+Thread.currentThread().getName();
			}

		});
		try {
			String res = task.get();
			System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		}*/

        for (int i = 0; i < 10; i++) {
            Runnable work = new WorkThread(i + "");
            eService.execute(work);
        }
        eService.shutdown();
        /**
         * shutdown
         将线程池里的线程状态设置成SHUTDOWN状态, 然后中断所有没有正在执行任务的线程.
         shutdownNow
         将线程池里的线程状态设置成STOP状态, 然后停止所有正在执行或暂停任务的线程.
         */
        while (!eService.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }

}
