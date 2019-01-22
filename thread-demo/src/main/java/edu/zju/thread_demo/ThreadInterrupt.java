package edu.zju.thread_demo;

import java.util.concurrent.TimeUnit;

public class ThreadInterrupt {
	public static void main(String[] args) throws InterruptedException {
		interruptWait();
	}

	public static void interruptTest() throws InterruptedException {
		Thread t = new Thread(new Runnable() {
			public void run() {
				Thread.currentThread().interrupt();
				System.out.println("interrupted");
				// true
				System.out.println("INNER TEST: " + Thread.interrupted()); // interrupt status 清除
				// false
				System.out.println("INNERT SEC TEST: " + Thread.interrupted());
			}
		});
		t.start();
		while (t.isAlive()) {
			System.out.println(t.isInterrupted()); // interrupt status 不会被清除。
		}
	}

	// interrupt sleep
	public static void interruptSleep() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					while (true) {
						System.out.println("SLEEP " + Thread.currentThread().getName());
						TimeUnit.SECONDS.sleep(2);
					}
				} catch (InterruptedException ie) {
					System.out.println("Being interrupted.");
				}
			}
		});
		t.start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException ie) {

		}
		t.interrupt();
		while (t.isAlive()) {
			System.out.println(t.isInterrupted());
		}
	}

	// interrupt wait
	public static void interruptWait() {
		final String s = new String("123");
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					synchronized (s) {
						s.wait();
					}
				} catch (InterruptedException ie) {
					System.out.println("Interrupt wait.");
				}
			}
		});
		t.start();
//		t.interrupt();
		try {
		TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		synchronized (s) {
			s.notify();
		}
	}
}
