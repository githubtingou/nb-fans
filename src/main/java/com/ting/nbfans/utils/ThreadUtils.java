package com.ting.nbfans.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 */
@Configuration
public class ThreadUtils {
    private static final int CORE_SIZE = Runtime.getRuntime().availableProcessors();


    @Bean(name = "executor")
    public ThreadPoolExecutor executor() {
        return new ThreadPoolExecutor(CORE_SIZE,
                CORE_SIZE * 2, 20,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(20),
                runnable -> new Thread(runnable, "fans-"),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
