package com.yr.encoder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;  
  
/**
 * 客户端 Handler
 * 解决粘包,半包 -- 定长解码方式
 * 
 * @author zxy-un
 * 
 * 2018-下午4:32:07
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {  
      
    @Override  
    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
    	String message = "邹想云"; // 消息
    	String body = "Baby I'm preying on you tonight"; // 内容
    	Pojo customMsg = new Pojo(message, message.length(), body.length(), body); // 构造方法 
//    	System.out.println("客户端 : " + customMsg.toString());
        ctx.writeAndFlush(customMsg);  
    }  
  
}  