package com.search.trek.infrastructure.client;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Test {
    public static void main(String[] args) {
        int bits = Integer.SIZE - 3;
        int running = -1 << bits;
        System.out.println("running = " + running);
        int shutdown = 0 << bits;
        System.out.println("shutdown = " + shutdown);
        int stop = 1 << bits;
        System.out.println("stop = " + stop);
        int tidying = 2 << bits;
        System.out.println("tidying = " + tidying);
        int terminated = 3 << bits;
        System.out.println("terminated = " + terminated);
        int ctl = running | 0;
        System.out.println("ctl = " + ctl);
        int capacity = (1 << bits) - 1;
        System.out.println("capacity = " + capacity);
        int workcountof = 536870912 & capacity;
        System.out.println("workcountof = " + workcountof);

        MonitoredThreadPoolExecutor monitoredThreadPoolExecutor = new MonitoredThreadPoolExecutor(10, 20, 3, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(1000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());

    }

    @Slf4j
    public static class MonitoredThreadPoolExecutor extends ThreadPoolExecutor {

        //任务提交成功时间
        private final ConcurrentHashMap<Runnable, Long> timeOfRequest = new ConcurrentHashMap<>();
        //任务实际开始执行时间
        private final ThreadLocal<Long> startTime = new ThreadLocal<>();
        //上一个任务提交成功时间
        private long lastArrivalTime;

        // 任务实际执行总数
        private final AtomicInteger numberOfRequestsRetired = new AtomicInteger();
        // 任务提交总数
        private final AtomicInteger numberOfRequests = new AtomicInteger();
        // 任务实际执行总耗时
        private final AtomicLong totalServiceTime = new AtomicLong();
        // 任务在队列等待总耗
        private final AtomicLong totalPoolTime = new AtomicLong();
        // 新任务提交总耗时
        private final AtomicLong aggregateInterRequestArrivalTime = new AtomicLong();


        public MonitoredThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                           BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        @Override
        protected void beforeExecute(Thread worker, Runnable task) {
            super.beforeExecute(worker, task);
            startTime.set(System.nanoTime());
        }

        @Override
        protected void afterExecute(Runnable task, Throwable t) {
            try {
                long start = startTime.get();
                totalServiceTime.addAndGet(System.nanoTime() - start);
                totalPoolTime.addAndGet(start - timeOfRequest.remove(task));
                numberOfRequestsRetired.incrementAndGet();
            } finally {
                if (null != t) {
                    log.error( "线程池处理异常:", t);
                }
                super.afterExecute(task, t);
            }
        }

        @Override
        public void execute(Runnable task) {
            long now = System.nanoTime();
            numberOfRequests.incrementAndGet();
            synchronized (this) {
                if (lastArrivalTime != 0L) {
                    aggregateInterRequestArrivalTime.addAndGet(now - lastArrivalTime);
                }
                lastArrivalTime = now;
                timeOfRequest.put(task, now);
            }
            super.execute(task);
        }
    }
}
