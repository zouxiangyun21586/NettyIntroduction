package encoder.custom;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;  

/**
 * 编码
 * @作者  罗玲红
 *
 * @时间 2017年6月14日 上午11:24:31
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
        out.writeInt(bodyBytes.length);   //先将消息长度写入，也就是消息头
        out.writeBytes(bodyBytes);   //消息体中包含我们要发送的数据
          
    }  
  
}  