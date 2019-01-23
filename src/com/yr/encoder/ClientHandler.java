package com.yr.encoder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;  
  
/**
 * �ͻ��� Handler
 * ���ճ��,��� -- �������뷽ʽ
 * 
 * @author zxy-un
 * 
 * 2018-����4:32:07
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {  
      
    @Override  
    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
    	String message = "������"; // ��Ϣ
    	String body = "Baby I'm preying on you tonight"; // ����
    	Pojo customMsg = new Pojo(message, message.length(), body.length(), body); // ���췽�� 
//    	System.out.println("�ͻ��� : " + customMsg.toString());
        ctx.writeAndFlush(customMsg);  
    }  
  
}  