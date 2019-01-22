package edu.zju.io_demo.aio_demo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.CountDownLatch;

import edu.zju.io_demo.Util;

import java.net.InetSocketAddress;
import java.nio.channels.CompletionHandler;
import java.nio.ByteBuffer;

public class AsyncTimeClientHandler implements Runnable, CompletionHandler<Void, AsyncTimeClientHandler> {
	private AsynchronousSocketChannel client;
	private String host;
	private int port;
	private CountDownLatch latch;

	public AsyncTimeClientHandler(String host, int port) {
		this.host = host;
		this.port = port;
		try {
			client = AsynchronousSocketChannel.open();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public void run() {
		latch = new CountDownLatch(1);
		client.connect(new InetSocketAddress(host, port), this, this);
		try {
			latch.await();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

	@Override
	public void completed(Void result, AsyncTimeClientHandler attachment) {
		// TODO Auto-generated method stub
		byte[] req = "REQUEST MESSAGE".getBytes();
		ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
		writeBuffer.put(req);
		writeBuffer.flip();
		client.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {

			@Override
			public void completed(Integer result, ByteBuffer attachment) {
				// TODO Auto-generated method stub
				if (attachment.hasRemaining())
					client.write(attachment, attachment, this);
				else {
					ByteBuffer readBuffer = ByteBuffer.allocate(1024);
					client.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>(){

						@Override
						public void completed(Integer result, ByteBuffer attachment) {
							// TODO Auto-generated method stub
							attachment.flip();
							byte[] body = new byte[attachment.remaining()];
							attachment.get(body);
							try {
								String msg = new String(body, "UTF-8");
								Util.print(msg);
								latch.countDown();
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void failed(Throwable exc, ByteBuffer attachment) {
							// TODO Auto-generated method stub
							try {
								client.close();
								latch.countDown();
							} catch (IOException ioe) {
								ioe.printStackTrace();
							}
						}
						
					});
				}
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				// TODO Auto-generated method stub
				try {
					client.close();
					latch.countDown();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		});
	}

	@Override
	public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
		// TODO Auto-generated method stub
		try {
			client.close();
			latch.countDown();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
