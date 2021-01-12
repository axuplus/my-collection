package com.utils.tools.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.*;


/***
 * ========================                           ========================
 * ========================Executors包下面的线程池类型========================
 * ========================          7种              ========================
 * Executors.newCachedThreadPool();
 * Executors.newFixedThreadPool();
 * Executors.newScheduledThreadPool();
 * Executors.newSingleThreadExecutor();
 * Executors.newSingleThreadScheduledExecutor()
 * Executors.newWorkStealingPool();
 * ForkJoinPool
 */

/***
 * ========================                           ========================
 * ======================== ThreadPoolExecutor七大参数 =======================
 * ========================                           ========================
 * #默认值
 * corePoolSize=1
 * maximumPoolSize=Integer.MAX_VALUE
 * TimeUnit=TimeUnit.SECONDS
 * keepAliveTime=1s
 * workQueue=BlockingQueue
 * allowCoreThreadTimeout=false
 * rejectedExecutionHandler=AbortPolicy()
 */

/***
 * ========================                           ========================
 * ========================    ForkJoinPool核心详解   ========================
 * ========================                           ========================
 * ForkJoinPool的两大核心就是分而治之(Divide and conquer)和工作窃取(Work Stealing)算法：
 * 1：分冶法：(Divide and conquer)
 *   思想：
 *      把一个规模大的问题划分为规模较小的子问题，然后分而治之，最后合并子问题的解得到原问题的解。
 *      在分治法中，子问题一般是相互独立的，因此，经常通过递归调用算法来求解子问题。
 *   步骤：
 *      （1）分割原问题：
 *      （2）求解子问题：
 *      （3）合并子问题的解为原问题的解。
 *   场景：
 *        二分搜索、大整数乘法、归并排序、快速排序、线性时间选择、汉诺塔
 * 2：窃取法：(Work Stealing)
 *  每个线程都有自己的一个WorkQueue，该工作队列是一个双端队列。队列支持三个功能push、pop、poll
 *  push/pop只能被队列的所有者线程调用，而poll可以被其他线程调用。划分的子任务调用fork时，都会被push到自己的队列中。
 *  默认情况下，工作线程从自己的双端队列获出任务并执行。当自己的队列为空时，线程随机从另一个线程的队列末尾调用poll方法窃取任务。
 *  它可以使用 Work Stealing 算法和双端队列很好地平衡了各线程的负载。
 *
 *
 *
 * 虽说了ForkJoinPool会把大任务拆分成多个子任务，但是ForkJoinPool并不会为每个子任务创建单独的线程。
 * 相反，池中每个线程都有自己的双端队列(Deque)用于存储任务，这个双端队列对于工作窃取算法至关重要。
 * （1）每个工作线程都有自己的工作队列WorkQueue；
 * （2）这是一个双端队列，它是线程私有的；
 * （3）ForkJoinTask中fork的子任务，将放入运行该任务的工作线程的队头，工作线程将以LIFO的顺序来处理工作队列中的任务；
 * （4）为了最大化地利用CPU，空闲的线程将从其它线程的队列中“窃取”任务来执行；
 * （5）从工作队列的尾部窃取任务，以减少竞争；
 * （6）双端队列的操作：push()/pop()仅在其所有者工作线程中调用，poll()是由其它线程窃取任务时调用的；
 * （7）当只剩下最后一个任务时，还是会存在竞争，是通过CAS来实现的；
 */

@SpringBootTest
@Slf4j
public class ForkJoinController {


    @Test
    public void testForkJoin() throws ExecutionException, InterruptedException {
        // RecursiveAction：无返回值的任务，通常用于只fork不join的情形。
        // RecursiveTask：有返回值的任务，通常用于fork+join的情形。
        // execute没有返回值
        // submit有返回值

        // 计算(0+1+2+3+1000000000)*2的结果
        int count = 1000000001;
        // 单线程计算
        long start1 = System.currentTimeMillis();
        log.info("1.第一种计算方式--单线程计算");
        long result = 0L;
        for (long i = 0; i < count; i++) {
            result += i;
        }
        log.info("1.计算结果：" + result + ",用时：" + (System.currentTimeMillis() - start1) + "ms.\n");

        // 通过ForkJoin框架进行子任务计算
        long start2 = System.currentTimeMillis();
        log.info("2.第二种计算方式--ForkJoin框架计算");
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        LargeSetComputeTask computeTask = new LargeSetComputeTask(0, count);
        Future<Long> future = forkJoinPool.submit(computeTask);
        forkJoinPool.shutdown();
        log.info("2.计算结果：" + future.get() + ",用时：" + (System.currentTimeMillis() - start2) + "ms.");
    }


    /***
     * 双端队列ArrayDeque
     * ArrayDeque 是基于数组实现的可动态扩容的双端队列，也就是说你可以在队列的头和尾同时插入和弹出元素。
     * 当元素数量超过数组初始化长度时，则需要扩容和迁移数据。
     */
    @Test
    public void testArrayDeque() {
        Deque<String> deque = new ArrayDeque<>(1);
        // 头插 (head push)
        deque.push("a");
        deque.push("b");
        deque.push("c");
        deque.push("d");
        // 尾插 (tail offerLast)
        deque.offerLast("e");
        deque.offerLast("f");
        deque.offerLast("g");
        deque.offerLast("h");
        // 这个时候会扩容
        deque.push("i");
        deque.offerLast("j");

        System.out.println("数据出栈：");
        while (!deque.isEmpty()) {
            // i d c b a e f g h j
            System.out.print(deque.pop() + " ");
        }
    }

    @Test
    public void printThreadPoolExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2), new ThreadFactoryBuilder().setNameFormat("retryClient-pool-").build(), new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolExecutor.submit(() -> {
            log.info("--记忆中的颜色是什么颜色---");
        });
        int poolSize = threadPoolExecutor.getPoolSize();
        log.error("==========poolSize {}", poolSize);
        ThreadFactory threadFactory = threadPoolExecutor.getThreadFactory();
        log.error("==========threadFactory {}", threadFactory);
        BlockingQueue<Runnable> queue = threadPoolExecutor.getQueue();
        log.error("==========queue {}", queue);
        RejectedExecutionHandler rejectedExecutionHandler = threadPoolExecutor.getRejectedExecutionHandler();
        log.error("==========rejectedExecutionHandler {}", rejectedExecutionHandler);
        int activeCount = threadPoolExecutor.getActiveCount();
        log.error("==========activeCount {}", activeCount);
        long completedTaskCount = threadPoolExecutor.getCompletedTaskCount();
        log.error("==========completedTaskCount {}", completedTaskCount);
        long keepAliveTime = threadPoolExecutor.getKeepAliveTime(TimeUnit.SECONDS);
        log.error("==========keepAliveTime {}", keepAliveTime);
        int maximumPoolSize = threadPoolExecutor.getMaximumPoolSize();
        log.error("==========maximumPoolSize {}", maximumPoolSize);
        long taskCount = threadPoolExecutor.getTaskCount();
        log.error("==========taskCount {}", taskCount);
        int largestPoolSize = threadPoolExecutor.getLargestPoolSize();
        log.error("==========largestPoolSize {}", largestPoolSize);
        int corePoolSize = threadPoolExecutor.getCorePoolSize();
        log.error("==========corePoolSize {}", corePoolSize);
    }


    @Test
    public void printForkJoin() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        for (int i = 0; i < 10; i++) {
            ForkJoinTask task = forkJoinPool.submit(new Fibonacci(i));
            System.out.println(task.get());
        }
    }

    static class Fibonacci extends RecursiveTask<Integer> {
        int n;

        public Fibonacci(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }
            Fibonacci fib1 = new Fibonacci(n - 1);
            fib1.fork();
            Fibonacci fib2 = new Fibonacci(n - 2);
            fib2.fork();
            return fib1.join() + fib2.join();
        }
    }
}

