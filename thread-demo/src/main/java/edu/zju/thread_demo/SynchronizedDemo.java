package edu.zju.thread_demo;

public class SynchronizedDemo {
	public static void main(String[] args) {
		repeatLock();
	}

	public static void repeatLock() {
		final B b = new B();
		@SuppressWarnings("unused")
		final A a = (A) b;
		Thread t = new Thread(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName() + " run");
				try {
					Thread.sleep(500);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
				b.h();
			}
		});
		t.start();
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException ie) {
//			ie.printStackTrace();
//		}
		b.f();  // 获取锁仍然是对象b
	}

}

class A {
	synchronized void f() {
		System.out.println(Thread.currentThread().getName() + " f run");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " f end");
	}
}

class B extends A {
	public synchronized void h() {
		try {
			System.out.println(Thread.currentThread().getName() + " h run");
			Thread.sleep(1000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		f();
		System.out.println(Thread.currentThread().getName() + "h end");
	}
}