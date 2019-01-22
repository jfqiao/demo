package edu.zju.thread_demo.multithread;

public class ThreadB extends Thread {
	
	private Service service;
	
	public ThreadB(Service service) {
		this.service = service;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			service.set();
		}
	}
	
}
