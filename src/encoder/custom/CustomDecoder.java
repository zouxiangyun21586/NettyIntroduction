package encoder.custom;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * netty-��Ϣͷ����Ϣ�������
 * @����  �����
 *
 * @ʱ�� 2017��6��14�� ����11:22:40
 */
public class CustomDecoder extends LengthFieldBasedFrameDecoder {  
      
    //�жϴ��Ϳͻ��˴��͹����������Ƿ���Э�鴫�䣬ͷ����Ϣ�Ĵ�СӦ���� byte+byte+int = 1+1+4 = 6  
    private static final int HEADER_SIZE = 6;  
      
    private byte type;  
      
    private byte flag;  
      
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
    public CustomDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,  
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
            throw new Exception("�ɶ���Ϣ�α�ͷ����Ϣ��С�����ڶ��ң�");  
        }  
          
        //ע���ڶ��Ĺ����У�readIndex��ָ��Ҳ���ƶ�  
        type = in.readByte();  
          
        flag = in.readByte();  
          
        length = in.readInt();  
          
        if (in.readableBytes() < length) {  
            throw new Exception("body�ֶ�������ҳ�����"+length+",������ʵ�����û����ô�࣬���ֶ��ң�");  
        }  
        ByteBuf buf = in.readBytes(length);  
        byte[] req = new byte[buf.readableBytes()];  
        buf.readBytes(req);  
        body = new String(req, "UTF-8");  
          
        CustomMsg customMsg = new CustomMsg(type,flag,length,body);  
        return customMsg;  
    }  
  
}  