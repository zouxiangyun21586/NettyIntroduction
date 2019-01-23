package com.yr.encoder;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;  

/**
 * 编码器
 * 解决粘包,半包 -- 定长解码方式
 * 
 * @author zxy-un
 * 
 * 2018-下午4:32:59
 */
public class Encoder extends MessageToByteEncoder<Pojo> {  
  
    @Override  
    protected void encode(ChannelHandlerContext ctx, Pojo msg, ByteBuf out) throws Exception {  
        if(null == msg){  
            throw new Exception("msg 为 null值");  
        }  
        String body = msg.getBody();  
        byte[] bodyBytes = body.getBytes(Charset.forName("utf-8"));
        String message = msg.getMessage();
        byte[] messageBytes = message.getBytes(Charset.forName("utf-8")); // 将String转byte[],因为 中文 的长度不一致,所以需转换成 byte数组
        out.writeInt(messageBytes.length); // 消息长度
//        out.writeInt(msg.getMessageLength()); // 仅限使用英文 或者 只有一个String 否则会有问题
        out.writeBytes(messageBytes);  // 消息
        out.writeInt(bodyBytes.length);
//        out.writeInt(msg.getLength());
        out.writeBytes(bodyBytes);
    }  
  
}  