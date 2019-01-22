package edu.zju;

public class InterruptWait {
	
	public static void main(String[] args) {
		Object o = new Object();
		Thread t = new Thread(new Runnable() {
			Object o1 = o;
			@Override
			public void run() {
				try {
					o1.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

}
