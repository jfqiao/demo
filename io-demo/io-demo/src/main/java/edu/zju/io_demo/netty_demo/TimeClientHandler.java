package edu.zju.io_demo.netty_demo;

import edu.zju.io_demo.Util;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter {
	private ByteBuf firstMsg;

	public TimeClientHandler() {
		byte[] reqMsg = "REQUEST MESSAGE".getBytes();
		firstMsg = Unpooled.buffer(reqMsg.length);
		firstMsg.writeBytes(reqMsg);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		byte[] msg = "HELLO\n".getBytes();
		for (int i = 0; i < 10; i++) {
			firstMsg = Unpooled.buffer(msg.length);
			firstMsg.writeBytes(msg);
			ctx.writeAndFlush(firstMsg);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		ByteBuf buf = (ByteBuf) msg;
//		byte[] resMsg = new byte[buf.readableBytes()];
//		buf.readBytes(resMsg);
//		String body = new String(resMsg, "UTF-8");
		Util.print(msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable t) {
		Util.print("Unexpected exception from downstream : " + t.getMessage());
		ctx.close();
	}
}
