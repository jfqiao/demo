package edu.zju.io_demo.aio_demo;

import java.nio.channels.CompletionHandler;

import edu.zju.io_demo.Util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

import java.util.Date;

public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer>{

	AsynchronousSocketChannel socketChannel;
	
	public ReadCompletionHandler(AsynchronousSocketChannel socketChannel) {
		if (this.socketChannel == null)
		this.socketChannel = socketChannel;
	}
	
	@Override
	public void completed(Integer result, ByteBuffer attachment) {
		// TODO Auto-generated method stub
		attachment.flip();
		byte[] body = new byte[attachment.remaining()];
		attachment.get(body);
		try {
			String msg = new String(body);
			Util.print(msg);
			this.doWrite(new Date().toString());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		// TODO Auto-generated method stub
		try {
			this.socketChannel.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
	}

	public void doWrite(String msg) throws IOException {
		if (msg != null && msg.length() > 0) {
			byte[]  writeMsg = msg.getBytes();
			ByteBuffer writeBuffer = ByteBuffer.allocate(writeMsg.length);
			writeBuffer.put(writeMsg);
			writeBuffer.flip();
			this.socketChannel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {

				@Override
				public void completed(Integer result, ByteBuffer attachment) {
					// TODO Auto-generated method stub
					if (attachment.hasRemaining())
						socketChannel.write(attachment, attachment, this);
				}

				@Override
				public void failed(Throwable exc, ByteBuffer attachment) {
					// TODO Auto-generated method stub
					try {
						socketChannel.close();
					} catch (IOException ioe) {
						// do nothing.
					}
				}
			});
		}
	}
}
