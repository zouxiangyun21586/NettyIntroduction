package encoder.custom;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;  
  
/**
 * 客户端
 * @作者  罗玲红
 *
 * @时间 2017年6月14日 上午11:29:10
 */
public class CustomClientHandler extends ChannelInboundHandlerAdapter {  
      
    @Override  
    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
    	String msg="我们是中国人";
        CustomMsg customMsg = new CustomMsg((byte)0xAB, (byte)0xCD, msg.length(), msg); 
        ctx.writeAndFlush(customMsg);  
    }  
  
}  