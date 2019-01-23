package com.yr.encoder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;  

/**
 * 服务端 Handler
 * 解决粘包,半包 -- 定长解码方式
 * 
 * @author zxy-un
 * 
 * 2018-下午7:11:55
 */
public class ServerHandler extends SimpleChannelInboundHandler<Object> {  
  
    @Override  
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {  
        if(msg instanceof Pojo) {  
            Pojo customMsg = (Pojo)msg;
            System.out.println("客户端--->服务端:  " + customMsg.toString());
//            System.out.println("客户端--->服务端:  "+ctx.channel().remoteAddress()+" 发送  "+customMsg.getBody());  
        }  
          
    }  
  
}  