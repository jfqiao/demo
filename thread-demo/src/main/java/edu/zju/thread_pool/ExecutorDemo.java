package edu.zju.thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {
	
	public static void main(String[] args) {
		 ExecutorService service = Executors.newFixedThreadPool(10);
		 service.submit(new Runnable() {
			public void run() {
				
			}
		 });
		 service.execute(new Runnable() {
			 public void run() {
				 
			 }
		 });
		 
	}

}
