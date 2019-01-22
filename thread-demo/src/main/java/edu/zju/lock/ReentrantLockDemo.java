package edu.zju.lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
	
	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		for (int i = 0; i < 10; i ++) {
			new Thread(() -> {
				lock.lock();
			} , "thread-" + i).start();
		}
		System.out.println("123");
	}

}
