package edu.zju.thread_local;

public class ThreadLocalDemo {
	public ThreadLocal<String> name = new ThreadLocal<>(); 
	
	public static void main(String[] args) {
		final ThreadLocalDemo obj = new ThreadLocalDemo();
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				obj.name.set(Thread.currentThread().getName());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO: handle exception
				}
				System.out.println(obj.name.get());
			}
		}, "Thread-1");
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				obj.name.set(Thread.currentThread().getName());
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(obj.name.get());
			}
		}, "Thread-2");
		t1.start();
		t2.start();
	}

}
