package encoder.zidai;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;  
    
public class TcpServer {    
    private static final int PORT = 9999;    
    /**���ڷ��䴦��ҵ���̵߳��߳������ */    
    protected static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors()*2; //Ĭ��    
    /** ҵ������̴߳�С*/    
    protected static final int BIZTHREADSIZE = 4;    
        /*  
     * NioEventLoopGroupʵ���Ͼ��Ǹ��̳߳�,  
     * NioEventLoopGroup�ں�̨������n��NioEventLoop������Channel�¼�,  
     * ÿһ��NioEventLoop������m��Channel,  
     * NioEventLoopGroup��NioEventLoop�����ﰤ��ȡ��NioEventLoop������Channel  
     */    
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);    
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE);    
        
    protected static void run() throws Exception {    
        ServerBootstrap b = new ServerBootstrap();    
        b.group(bossGroup, workerGroup);    
        b.channel(NioServerSocketChannel.class);    
        b.childHandler(new ChannelInitializer<SocketChannel>() {    
            @Override    
            public void initChannel(SocketChannel ch) throws Exception {    
                ChannelPipeline pipeline = ch.pipeline();    
                pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));    
                pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));    
                pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));    
                pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));    
                pipeline.addLast(new TcpServerHandler());    
            }    
        });    
    
        ChannelFuture f = b.bind(PORT).sync();
  
        f.channel().closeFuture().sync();  
          
    }    
        
    protected static void shutdown() {    
        workerGroup.shutdownGracefully();    
        bossGroup.shutdownGracefully();    
    }    
    
    public static void main(String[] args) throws Exception { 
        TcpServer.run();    
    }    
}    