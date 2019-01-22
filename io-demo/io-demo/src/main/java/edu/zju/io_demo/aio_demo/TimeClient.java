package edu.zju.io_demo.aio_demo;

public class TimeClient {
	public static void main(String[] args) {
		new Thread(new AsyncTimeClientHandler("localhost", 8080), "AIO-AsyncTimeClientHandler-01").start();
	}
}
