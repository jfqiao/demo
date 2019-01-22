package edu.zju.timer;

import java.util.Timer;
import java.util.Calendar;

public class TimerTest {
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " executitng.");
		MyTask task = new MyTask();
		Timer timer = new Timer();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.SECOND, 2);
		timer.schedule(task, c.getTime());
	}
}
