package edu.zju.io_demo.aio_demo;

import java.util.concurrent.CountDownLatch;

import edu.zju.io_demo.Util;

import java.io.IOException;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.net.InetSocketAddress;

public class AsyncTimeServerHandler implements Runnable {
    CountDownLatch latch;
	AsynchronousServerSocketChannel asynServerSocket;

	public AsyncTimeServerHandler(int port) {
		try {
			asynServerSocket = AsynchronousServerSocketChannel.open();
			asynServerSocket.bind(new InetSocketAddress(port));
			Util.print("Server start on port " + port);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public void run() {
		latch = new CountDownLatch(1);
		doAccept();
		try {
			latch.await();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
	
	public void doAccept() {
		this.asynServerSocket.accept(this, new AcceptCompletionHandler());
	}
}
