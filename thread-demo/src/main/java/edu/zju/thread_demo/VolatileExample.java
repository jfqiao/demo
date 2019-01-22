package edu.zju.thread_demo;

public class VolatileExample {
	int a = 0;
	boolean flag = false;
	
	public void writer() {
		a = 1;
		flag = true;
		try {
			Thread.sleep(1);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
	
	public void reader() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		if (flag) {
			int i = a;
			System.out.println(i);
		}
	}
	
	public static void main(String[] args) {
		final VolatileExample ve = new VolatileExample();
		Thread t = new Thread(new Runnable() {
			public void run() {
				ve.writer();
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				ve.reader();
			}
		});
		t.start();
		t2.start();
	}
}
