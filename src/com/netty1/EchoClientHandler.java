package com.netty1;
import io.netty.buffer.ByteBuf;  
import io.netty.buffer.ByteBufUtil;  
import io.netty.buffer.Unpooled;  
import io.netty.channel.ChannelHandlerContext;  
import io.netty.channel.SimpleChannelInboundHandler;  
import io.netty.channel.ChannelHandler.Sharable;  
import io.netty.util.CharsetUtil;  
  
@Sharable  
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {  
    /** 
     *�˷����������ӵ��������󱻵���  
     * */  
    public void channelActive(ChannelHandlerContext ctx) {  
        ctx.write(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));  
    }  
    /** 
     *�˷������ڽ��յ����������ݺ����  
     * */  
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {  
        System.out.println("Client received: " + ByteBufUtil.hexDump(in.readBytes(in.readableBytes())));  
    }  
    /** 
     *��׽���쳣  
     * */  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {  
        cause.printStackTrace();  
        ctx.close();  
    }  
  
}  