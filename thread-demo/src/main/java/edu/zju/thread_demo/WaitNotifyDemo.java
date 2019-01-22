package edu.zju.thread_demo;

public class WaitNotifyDemo {
	public static void main(String[] args) {
		waitTest();
	}

	public static void waitTest() {
		final String s = "123";
		Thread t = new Thread(new Runnable() {
			public void run() {
				synchronized (s) {
					System.out.println("Before wait.");
					try {
						s.wait(); // wait被唤醒后，需要等待锁才可以执行。
					} catch (InterruptedException ie) {
						ie.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName() + " notify from wait.");
				}
			}
		});
		t.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		synchronized (s) {
			try {
				s.notify(); // 任意唤醒一个在wait上等待的线程。
//				s.notifyAll();  // 唤醒所有在wait上等待的线程，但是执行仍需要等待获取锁。
				System.out.println(Thread.currentThread().getName() + " to nofify other thread");
				Thread.sleep(1000);
				System.out.println("Other thread notify by " + Thread.currentThread().getName()
						+ " wait util monitor was released.");
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

}
