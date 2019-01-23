package encoder.zidai;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;  
  
public class TcpClientHandler extends ChannelHandlerAdapter {    
    public TcpClientHandler() {    
    }    
       //链路链接成功  
    public void channelActive(ChannelHandlerContext ctx) throws Exception {    
        for (int i = 0; i < 1000; i++) { 
            ctx.writeAndFlush("1ac"); 
        }   
        // 链接成功后发送    
    }    
    
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {    
         System.out.println("client收到数据" +msg);  
       ctx.write("收到数据!");   
       ctx.write(msg);   
       ctx.write("w2d");   
    
    }    
    
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {    
        ctx.flush();    
    }    
    
    @Override    
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)    
            throws Exception {    
        cause.printStackTrace();    
        ctx.close();    
    }    
}  