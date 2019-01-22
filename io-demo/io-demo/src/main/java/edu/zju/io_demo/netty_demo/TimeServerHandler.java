package edu.zju.io_demo.netty_demo;

import edu.zju.io_demo.Util;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

public class TimeServerHandler extends ChannelHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) 
		throws Exception {
//		ByteBuf buf = (ByteBuf)msg;
//		byte[] req = new byte[buf.readableBytes()];
//		buf.readBytes(req);
//		String reqMsg = new String(req, "UTF-8");
		Util.print(msg);
		ByteBuf resp = Unpooled.copiedBuffer((new Date().toString() + "\n").getBytes());
		ctx.writeAndFlush(resp);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
		ctx.close();
	}
}
