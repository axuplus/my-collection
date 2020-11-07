package com.utils.executor;

import java.util.concurrent.RecursiveTask;

public class LargeSetComputeTask extends RecursiveTask<Long> {
    //阈值
    private static final int THRESHOLD = 100000;
    private int start;//开始下标
    private int end;//结束下标

    public LargeSetComputeTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        // 如果当前任务的计算量在阈值范围内，则直接进行计算
        if (end - start < THRESHOLD) {
            return computeByUnit();
        } else {// 如果当前任务的计算量超出阈值范围，则进行计算任务拆分
            // 计算中间索引
            int middle = (start + end) / 2;
            // 定义子任务-迭代思想
            LargeSetComputeTask left = new LargeSetComputeTask(start, middle);
            LargeSetComputeTask right = new LargeSetComputeTask(middle, end);
            // 划分子任务-fork
            left.fork();
            right.fork();
            // 合并计算结果-join
            return left.join() + right.join();
        }
    }
    private long computeByUnit() {
        long sum = 0L;
        for (int i = start; i < end; i++) {
            sum += i;
        }
        return sum;
    }
}
