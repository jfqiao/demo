package edu.zju.io_demo.bio_demo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeServerHandlerExecutorPool {

	private ExecutorService pool;
	
	public TimeServerHandlerExecutorPool(int maxPoolSize, int queueSize) {
		pool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L,
				 TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
		
	}
	
	public void execute(Runnable task) {
		pool.execute(task);
	}
}
