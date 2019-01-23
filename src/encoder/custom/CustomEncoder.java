package encoder.custom;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;  

/**
 * ����
 * @����  �����
 *
 * @ʱ�� 2017��6��14�� ����11:24:31
 */
public class CustomEncoder extends MessageToByteEncoder<CustomMsg> {  
  
    @Override  
    protected void encode(ChannelHandlerContext ctx, CustomMsg msg, ByteBuf out) throws Exception {  
        if(null == msg){  
            throw new Exception("msg is null");  
        }  
        String body = msg.getBody();  
        byte[] bodyBytes = body.getBytes(Charset.forName("utf-8"));  
        out.writeByte(msg.getType());  
        out.writeByte(msg.getFlag());  
        out.writeInt(bodyBytes.length);   //�Ƚ���Ϣ����д�룬Ҳ������Ϣͷ
        out.writeBytes(bodyBytes);   //��Ϣ���а�������Ҫ���͵�����
          
    }  
  
}  