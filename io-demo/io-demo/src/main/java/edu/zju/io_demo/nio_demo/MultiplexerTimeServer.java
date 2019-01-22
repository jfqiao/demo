package edu.zju.io_demo.nio_demo;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;

import edu.zju.io_demo.Util;

import java.nio.channels.SelectionKey;

import java.net.InetSocketAddress;

import java.util.Set;
import java.util.Iterator;
import java.util.Date;

public class MultiplexerTimeServer implements Runnable {

	private Selector selector;
	private ServerSocketChannel srvChannel;
	private volatile boolean stop;

	public MultiplexerTimeServer(int port) {
		try {
			selector = Selector.open();
			srvChannel = ServerSocketChannel.open();
			srvChannel.configureBlocking(false);
			srvChannel.socket().bind(new InetSocketAddress("localhost", port), 1024);
			srvChannel.register(selector, SelectionKey.OP_ACCEPT);
			Util.print("Server listens on port: " + port);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (!stop) {
			try {
				selector.select(1000);
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectedKeys.iterator();
				SelectionKey key = null;
				while (iterator.hasNext()) {
					key = iterator.next();
					iterator.remove();
					try {
					this.handleInput(key);
					} catch (IOException ioe) {
						ioe.printStackTrace();
						if (key != null) {
							key.cancel();
							if (key.channel() != null) 
								key.channel().close();
						}
					}
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		if (selector != null) {
			try {
				selector.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public void stop() {
		this.stop = true;
	}

	private void handleInput(SelectionKey key) throws IOException {
		if (key.isValid()) {
			if (key.isAcceptable()) {
				ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
				SocketChannel socket = ssc.accept();
				socket.configureBlocking(false);
				socket.register(selector, SelectionKey.OP_READ);
			}
			if (key.isReadable()) {
				SocketChannel socket = (SocketChannel)key.channel();
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = socket.read(readBuffer);
				if (readBytes > 0) {
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes, "UTF-8");
					Util.print(body);
					this.doWrite(socket, new Date().toString());
				} else if (readBytes < 0) {
					key.cancel();
					socket.close();
				} else {
					
				}
			}
		}
	}
	
	private void doWrite(SocketChannel socketChannel, String responseStr) 
		throws IOException {
		if (responseStr != null && responseStr.trim().length() > 0) {
			byte[] bytes = responseStr.getBytes();
			ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			socketChannel.write(writeBuffer);
		}
	}
}
