package edu.zju.io_demo.nio_demo;

public class TimeServer {
	
	public static void main(String[] args) {
		int port = 8080;
		MultiplexerTimeServer server = new MultiplexerTimeServer(port);
		new Thread(server, "NIO-MultiplexerServer").start();
	}

}
