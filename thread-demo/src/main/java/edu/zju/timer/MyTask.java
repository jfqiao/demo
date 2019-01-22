package edu.zju.timer;
import java.util.TimerTask;

public class MyTask extends TimerTask {
	@Override
	public void run() {
		System.out.println("Scheduling task running. Time: " + System.currentTimeMillis());
		this.cancel();
//		System.gc();
	}
}
