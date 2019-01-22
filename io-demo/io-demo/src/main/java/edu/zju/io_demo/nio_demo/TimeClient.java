package edu.zju.io_demo.nio_demo;

import java.nio.channels.Selector;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.net.InetSocketAddress;

import java.util.Set;
import java.util.Iterator;

import edu.zju.io_demo.Util;

import java.nio.ByteBuffer;
public class TimeClient implements Runnable{
	private int port;
	private String host;
	private SocketChannel socketChannel;
	private volatile boolean stop;
	private Selector selector;
	
	public static void main(String[] args) {
		String host = "localhost";
		int port = 8080;
		new Thread(new TimeClient(host, port)).start();
	}
	
	@Override
	public void run() {
		try {
			this.doConnect();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		while (!stop) {
			try {
				selector.select(1000);
				Set<SelectionKey> keys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = keys.iterator();
				SelectionKey key = null;
				while (iterator.hasNext()) {
					key = iterator.next();
					iterator.remove();
					try {
						handleInput(key);
					} catch (IOException ioe) {
						if (key != null)
							key.cancel();
						if (key.channel() != null)
							key.channel().close();
						ioe.printStackTrace();
					}
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		if (selector != null) {
			try {
				selector.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public TimeClient(String host, int port) {
		this.host = host;
		this.port = port;
		try {
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void doConnect() throws IOException {
		if (socketChannel.connect(new InetSocketAddress(host, port))) {
			socketChannel.register(selector, SelectionKey.OP_READ);
			this.doWrite(this.socketChannel); // 直接连接成功，发送消息。
		} else {
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}
	}
	
	public void handleInput(SelectionKey key) throws IOException {
		if (key.isValid()) {
			SocketChannel sc = (SocketChannel)key.channel();
			if (key.isConnectable()) {
				if (sc.finishConnect()) {
					sc.register(selector, SelectionKey.OP_READ);
					doWrite(sc);
				} else {
					System.exit(1);
				}
			}
			if (key.isReadable()) {
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if (readBytes > 0) {
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes, "UTF-8");
					Util.print(body);
					this.stop = true;
				} else if (readBytes < 0) {
					key.cancel();
					sc.close();
				}
			}
		}
	}
	
	private void doWrite(SocketChannel sc) throws IOException {
		byte[] reqMsg = "REQUEST MESSAGE.".getBytes();
		ByteBuffer writeBuffer = ByteBuffer.allocate(reqMsg.length);
		writeBuffer.put(reqMsg);
		writeBuffer.flip();
		sc.write(writeBuffer);
		if (!writeBuffer.hasRemaining()) {
			Util.print("Send success.");
		}
	}
}
