package com.yr.encoder;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;  

/**
 * ������
 * ���ճ��,��� -- �������뷽ʽ
 * 
 * @author zxy-un
 * 
 * 2018-����4:32:59
 */
public class Encoder extends MessageToByteEncoder<Pojo> {  
  
    @Override  
    protected void encode(ChannelHandlerContext ctx, Pojo msg, ByteBuf out) throws Exception {  
        if(null == msg){  
            throw new Exception("msg Ϊ nullֵ");  
        }  
        String body = msg.getBody();  
        byte[] bodyBytes = body.getBytes(Charset.forName("utf-8"));
        String message = msg.getMessage();
        byte[] messageBytes = message.getBytes(Charset.forName("utf-8")); // ��Stringתbyte[],��Ϊ ���� �ĳ��Ȳ�һ��,������ת���� byte����
        out.writeInt(messageBytes.length); // ��Ϣ����
//        out.writeInt(msg.getMessageLength()); // ����ʹ��Ӣ�� ���� ֻ��һ��String �����������
        out.writeBytes(messageBytes);  // ��Ϣ
        out.writeInt(bodyBytes.length);
//        out.writeInt(msg.getLength());
        out.writeBytes(bodyBytes);
    }  
  
}  