package encoder.custom;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;  
  
/**
 * �ͻ���
 * @����  �����
 *
 * @ʱ�� 2017��6��14�� ����11:29:10
 */
public class CustomClientHandler extends ChannelInboundHandlerAdapter {  
      
    @Override  
    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
    	String msg="�������й���";
        CustomMsg customMsg = new CustomMsg((byte)0xAB, (byte)0xCD, msg.length(), msg); 
        ctx.writeAndFlush(customMsg);  
    }  
  
}  