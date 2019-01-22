package edu.zju.final_demo;

public class FinalFieldOverflow {
	
	public final int i;
	public static FinalFieldOverflow obj;
	
	public FinalFieldOverflow() {
		obj = this;
		System.out.println(System.currentTimeMillis() + " Object assigned.");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		i = 1;
		System.out.println("Finished " + System.currentTimeMillis());
	}
	
	public static void reader() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (obj != null) {
			System.out.println("final field. " + obj.i);
		} else {
			System.out.println("object is null. " + System.currentTimeMillis());
		}
		try {
			Thread.sleep(1500);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		if (obj != null) {
			System.out.println("final field. " + obj.i);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(() -> FinalFieldOverflow.reader());
		Thread t2 = new Thread(() -> new FinalFieldOverflow());
		t.start();
		t2.start();
	}

}
