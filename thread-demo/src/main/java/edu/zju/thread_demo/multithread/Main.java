package edu.zju.thread_demo.multithread;

public class Main {
	public static void main(String[] args) {
		Service service = new Service();
		for (int i = 0; i < 5; i++) {
			ThreadA a = new ThreadA(service);
			ThreadB b = new ThreadB(service);
			a.start();
			b.start();
		}
	}
}
