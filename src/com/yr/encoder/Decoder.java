package com.yr.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * netty-��Ϣͷ����Ϣ��( ������ )
 * ���ճ��,��� -- �������뷽ʽ
 * 
 * @author zxy-un
 * 
 * 2018-����4:32:25
 */
public class Decoder extends LengthFieldBasedFrameDecoder {  
      
    //�жϴ��Ϳͻ��˴��͹����������Ƿ���Э�鴫�䣬ͷ����Ϣ�Ĵ�СӦ���� byte+byte+int = 1+1+4 = 6  
    private static final int HEADER_SIZE = 8;  
      
    private String message;
    
    private int messageLength;

    private int length;  
      
    private String body;  
  
    /** 
     *  
     * @param maxFrameLength ����ʱ������ÿ��֡���ݵ���󳤶� 
     * @param lengthFieldOffset ��֡�����У���Ÿ�֡���ݵĳ��ȵ����ݵ���ʼλ�� 
     * @param lengthFieldLength ��¼��֡���ݳ��ȵ��ֶα���ĳ��� 
     * @param lengthAdjustment �޸�֡���ݳ����ֶ��ж����ֵ������Ϊ���� 
     * @param initialBytesToStrip ������ʱ����Ҫ�������ֽ��� 
     * @param failFast Ϊtrue����frame���ȳ���maxFrameLengthʱ������TooLongFrameException�쳣��Ϊfalse����ȡ������֡�ٱ��쳣 
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
            throw new Exception("�ɶ���Ϣ�α�ͷ����ϢС");  
        }  
          
        //ע�� :  �ڱ���ʱʲô˳�����ľͰ�ʲô˳��ȡ��,����ȡֵ��������
        messageLength = in.readInt(); // int ����˳���,�ȴ����ĸ�ֵ����ȡ���ĸ�ֵ
          
        if (in.readableBytes() < messageLength) {  
            throw new Exception("message �ֶγ����� : " + messageLength + " , ��ʵ�����û����ô��.");  
        }  
        ByteBuf messageBB = in.readBytes(messageLength);  
        byte[] messageByte = new byte[messageBB.readableBytes()];  
        messageBB.readBytes(messageByte);  
        message = new String(messageByte, "UTF-8");
        
        
        length = in.readInt();
        
        if (in.readableBytes() < length) {  
            throw new Exception("body �ֶγ����� : " + length + " ,����ʵ�����û����ô��.");  
        }  
        ByteBuf bodyBB = in.readBytes(length);  
        byte[] bodyByte = new byte[bodyBB.readableBytes()];  
        bodyBB.readBytes(bodyByte);  
        body = new String(bodyByte, "UTF-8");  
          
        Pojo customMsg = new Pojo(message,messageLength,length,body);
        return customMsg;  
    }  
  
}  