package encoder.custom;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;  
/**
 * �����  
 * @����  �����
 *
 * @ʱ�� 2017��6��14�� ����11:29:21
 */
public class CustomServerHandler extends SimpleChannelInboundHandler<Object> {  
  
    @Override  
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {  
        if(msg instanceof CustomMsg) {  
            CustomMsg customMsg = (CustomMsg)msg;  
            System.out.println("�ͻ���--->�����:  "+ctx.channel().remoteAddress()+" ����  "+customMsg.getBody());  
        }  
          
    }  
  
}  