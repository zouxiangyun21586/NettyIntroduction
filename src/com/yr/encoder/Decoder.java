package com.yr.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * netty-消息头和消息体( 解码器 )
 * 解决粘包,半包 -- 定长解码方式
 * 
 * @author zxy-un
 * 
 * 2018-下午4:32:25
 */
public class Decoder extends LengthFieldBasedFrameDecoder {  
      
    //判断传送客户端传送过来的数据是否按照协议传输，头部信息的大小应该是 byte+byte+int = 1+1+4 = 6  
    private static final int HEADER_SIZE = 8;  
      
    private String message;
    
    private int messageLength;

    private int length;  
      
    private String body;  
  
    /** 
     *  
     * @param maxFrameLength 解码时，处理每个帧数据的最大长度 
     * @param lengthFieldOffset 该帧数据中，存放该帧数据的长度的数据的起始位置 
     * @param lengthFieldLength 记录该帧数据长度的字段本身的长度 
     * @param lengthAdjustment 修改帧数据长度字段中定义的值，可以为负数 
     * @param initialBytesToStrip 解析的时候需要跳过的字节数 
     * @param failFast 为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异常 
     */  
    public Decoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,  
            int lengthAdjustment, int initialBytesToStrip, boolean failFast) {  
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength,  
                lengthAdjustment, initialBytesToStrip, failFast);  
    }  
      
    @Override  
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {  
        if (in == null) {  
            return null;  
        }  
        if (in.readableBytes() < HEADER_SIZE) {  
            throw new Exception("可读信息段比头部信息小");  
        }  
          
        //注意 :  在编码时什么顺序存入的就按什么顺序取出,否则取值会有问题
        messageLength = in.readInt(); // int 是有顺序的,先存入哪个值就先取出哪个值
          
        if (in.readableBytes() < messageLength) {  
            throw new Exception("message 字段长度是 : " + messageLength + " , 但实际情况没有这么多.");  
        }  
        ByteBuf messageBB = in.readBytes(messageLength);  
        byte[] messageByte = new byte[messageBB.readableBytes()];  
        messageBB.readBytes(messageByte);  
        message = new String(messageByte, "UTF-8");
        
        
        length = in.readInt();
        
        if (in.readableBytes() < length) {  
            throw new Exception("body 字段长度是 : " + length + " ,但真实情况是没有这么多.");  
        }  
        ByteBuf bodyBB = in.readBytes(length);  
        byte[] bodyByte = new byte[bodyBB.readableBytes()];  
        bodyBB.readBytes(bodyByte);  
        body = new String(bodyByte, "UTF-8");  
          
        Pojo customMsg = new Pojo(message,messageLength,length,body);
        return customMsg;  
    }  
  
}  