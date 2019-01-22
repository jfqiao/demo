package edu.zju.thread_demo.multithread;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Service {
	private ReentrantLock lock = new ReentrantLock(false);
	private Condition condition = lock.newCondition();
	private boolean flag = true;
	
	public void get() {
		try {
			lock.lock();
			while (flag) {
				System.out.println("@@");
				condition.await();
			}
			flag = true;
			System.out.println("@");
			condition.signalAll();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public void set() {
		try {
			lock.lock();
			while (!flag) {
				System.out.println("**");
				condition.await();
			}
			flag = false;
			System.out.println("*");
			condition.signalAll();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}
