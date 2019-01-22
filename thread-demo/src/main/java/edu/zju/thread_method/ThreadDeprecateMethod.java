package edu.zju.thread_method;

public class ThreadDeprecateMethod {

	public static void main(String[] args) {
		Object o = new Object();
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					System.out.println(System.currentTimeMillis());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					synchronized (o) {
						Thread.currentThread().suspend();
					}
				}
			}

		});
		t1.start();
		
		Thread t2 = new Thread(new Runnable() {

			Thread t = t1;

			@Override
			public void run() {
				t.suspend();
			}
		});
		Thread t3 = new Thread(new Runnable() {
			Thread t;

			@Override
			public void run() {
				t = t1;
				while (true) {
					synchronized (o) {
						t.resume();
						System.out.println("T3");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
//				System.out.println("exit t3");
			}
		});
		// t3.start();
		// t2.start();
		t3.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("123");
//		t1.resume();
	}

}
