package edu.zju.io_demo.aio_demo;

import java.nio.channels.CompletionHandler;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.ByteBuffer;

public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler> {

	@Override
	public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
		// TODO Auto-generated method stub
		attachment.latch.countDown();
	}

	@Override
	public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
		// TODO Auto-generated method stub
		attachment.asynServerSocket.accept(attachment, this);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		result.read(buffer, buffer, new ReadCompletionHandler(result));
	}

}
