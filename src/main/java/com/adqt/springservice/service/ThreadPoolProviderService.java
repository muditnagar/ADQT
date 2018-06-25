package com.adqt.springservice.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

@Service
public class ThreadPoolProviderService {

	private ExecutorService pool;

	private Integer threadPoolSize;

	private static final Integer DEFAULT_THREAD_POOL_SIZE = new Integer(10);

	@PostConstruct
	private void intializePool() {
		threadPoolSize = DEFAULT_THREAD_POOL_SIZE;
		pool = Executors.newFixedThreadPool(threadPoolSize);
	}
	
	public <T> ExecutorCompletionService<T> getExecutorCompletionService(){
		return new ExecutorCompletionService<T>(pool);
	}
	
	public void execute(Runnable task) {
		pool.submit(task);
	}
	
	public void submit(Runnable task){
		pool.submit(task);
	}
	
	public <T> Future<T> submit(Callable<T> task){
		return pool.submit(task);
	}
	
	@PreDestroy
	private void cleanup(){
		pool.shutdown();
	}
}