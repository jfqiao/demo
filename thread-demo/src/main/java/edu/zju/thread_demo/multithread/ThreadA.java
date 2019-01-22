package edu.zju.thread_demo.multithread;

public class ThreadA extends Thread {
	
	private Service service;
	
	public ThreadA(Service service) {
		this.service = service;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			service.get();
		}
	}
}
