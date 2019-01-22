package edu.zju.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;
import java.util.ArrayList;

public class SafeCounter {
	
	private AtomicInteger atomicI = new AtomicInteger(0);
	
	private int i = 0;
	
	public static void main(String[] args) {
		final SafeCounter counter = new SafeCounter();
		List<Thread> ts = new ArrayList<>(600);
		for (int j = 0; j < 100; j++) {
			Thread t = new Thread(() ->  {
				for (int i = 0; i < 10000; i++) {
					counter.safeCount();
					counter.count();
				}
			});
			ts.add(t);
		}
		for (Thread t : ts) {
			t.start();
		}
		for (Thread t : ts) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(counter.i);
		System.out.println(counter.atomicI.get());
	}
	
	public void safeCount() {
		for (;;) {
			int i = atomicI.get();
			boolean suc = atomicI.compareAndSet(i, i + 1);
			if (suc)
				break;
		}
	}
	
	public void count() {
		this.i++;
	}
}
