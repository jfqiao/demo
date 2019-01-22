package edu.zju.thread_demo;

public class ThreadGroupDemo {

	public static void main(String[] args) {
		enumerate();
	}
	
	public static void activeCount() {
		System.out.println(Thread.activeCount());
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(1000);
				} catch(InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}, "thread1");
		t.start();
		System.out.println(Thread.activeCount());
	}
	
	public static void enumerate() {
		activeCount();
		Thread[] threads = new Thread[2];
		Thread.enumerate(threads);
		for (Thread thread : threads)
			System.out.println(thread.getName());
	}
}
