package com.yr.encoder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;  

/**
 * ����� Handler
 * ���ճ��,��� -- �������뷽ʽ
 * 
 * @author zxy-un
 * 
 * 2018-����7:11:55
 */
public class ServerHandler extends SimpleChannelInboundHandler<Object> {  
  
    @Override  
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {  
        if(msg instanceof Pojo) {  
            Pojo customMsg = (Pojo)msg;
            System.out.println("�ͻ���--->�����:  " + customMsg.toString());
//            System.out.println("�ͻ���--->�����:  "+ctx.channel().remoteAddress()+" ����  "+customMsg.getBody());  
        }  
          
    }  
  
}  