package edu.zju.thread_demo;

import java.util.concurrent.TimeUnit;

public class DaemonThreadDemo {

	public static void daemonThread(final int i) {
		Thread daemonThread = new Thread(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("Start daemon thread: " + i);
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					System.out.println("Run ?.");
				}
				// 如果所有非守护线程结束了仍未执行完毕，则直接退出，无法打印该语句。
				System.out.println("End daemon thread: " + i);
			}
		});
		daemonThread.setDaemon(true);
		daemonThread.start();
	}

	public static void main(String[] args) {
		int j = 1;
		daemonThread(j);
		try {
			// 不沉睡当前线程，会导致用户线程很快退出，守护线程可能不会执行到打印语句。
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
