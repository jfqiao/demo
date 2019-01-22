package edu.zju.thread_demo;

import java.lang.Thread.UncaughtExceptionHandler;

public class UncaughtHandlerDemo {

	public static void main(String[] args) {
		uncaughtHandler();
	}
	
	public static void uncaughtHandler() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				System.out.println(1 / 0);
			}
		}, "uncaught exception thread");
		t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			public void uncaughtException(Thread t, Throwable e) {
				// TODO Auto-generated method stub
				System.out.println(t.getName());
				System.out.println(e.getMessage());
			}
			
		});
		t.start();
	}
}
