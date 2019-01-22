package edu.zju.io_demo.aio_demo;

public class TimeServer {
	public static void main(String[] args) {
		new Thread(new AsyncTimeServerHandler(8080), "AIO-AsyncTimeServerHandler-01").start();
	}
}
