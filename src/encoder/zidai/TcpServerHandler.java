package encoder.zidai;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;  
    
public class TcpServerHandler extends ChannelHandlerAdapter  {    
    
    public void channelRead(ChannelHandlerContext ctx, Object msg) {  
       ctx.write(msg); 
       String obj= (String)msg;  
       System.out.println("访问数据"+obj);  
       ctx.flush(); 
  
    }  
  
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {   
       cause.printStackTrace();  
       ctx.close();  
    }  
    public void channelActive(final ChannelHandlerContext ctx) {  
            ctx.writeAndFlush("有客户端连接");  
    }  
    
}    