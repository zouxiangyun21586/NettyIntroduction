package encoder.custom;
import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;  
  
public class CustomServer {  
      
	/**
	 * ����ÿ��֡���ݵ���󳤶� 
	 */
    private static final int MAX_FRAME_LENGTH = 1024 * 1024;  
    /**
     * ��¼��֡���ݳ��ȵ��ֶα���ĳ��� 
     */
    private static final int LENGTH_FIELD_LENGTH = 4; 
    /**
     *  ��֡�����У���Ÿ�֡���ݵĳ��ȵ����ݵ���ʼλ�� 
     */
    private static final int LENGTH_FIELD_OFFSET = 2;  
    /**
     * �޸�֡���ݳ����ֶ��ж����ֵ������Ϊ����
     */
    private static final int LENGTH_ADJUSTMENT = 0;  
    /**
     * ������ʱ����Ҫ�������ֽ��� 
     */
    private static final int INITIAL_BYTES_TO_STRIP = 0;  
    /**
     * Ϊtrue����frame���ȳ���maxFrameLengthʱ������TooLongFrameException�쳣��
     * Ϊfalse����ȡ������֡�ٱ��쳣 
     */
    private static final boolean failFast=false;
    
    /**
     * �˿�
     */
    private int port;  
      
    public CustomServer(int port) {  
        this.port = port;  
    }  
      
    public void start(){  
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        try {  
            ServerBootstrap sbs = new ServerBootstrap().group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))  
                    .childHandler(new ChannelInitializer<SocketChannel>() {  
                          
                        protected void initChannel(SocketChannel ch) throws Exception {  
                             ch.pipeline().addLast(new CustomDecoder(MAX_FRAME_LENGTH,LENGTH_FIELD_LENGTH,LENGTH_FIELD_OFFSET,LENGTH_ADJUSTMENT,INITIAL_BYTES_TO_STRIP,failFast));  
                             ch.pipeline().addLast(new CustomServerHandler());  
                        };  
                          
                    }).option(ChannelOption.SO_BACKLOG, 128)     
                    .childOption(ChannelOption.SO_KEEPALIVE, true);  
             // �󶨶˿ڣ���ʼ���ս���������  
             ChannelFuture future = sbs.bind(port).sync();    
               
             System.out.println("����ʼ������   " + port+" �˿�" );  
             future.channel().closeFuture().sync();  
        } catch (Exception e) {  
            bossGroup.shutdownGracefully();  
            workerGroup.shutdownGracefully();  
        }  
    }  
      
    public static void main(String[] args) throws Exception {  
        int port;  
        if (args.length > 0) {  
            port = Integer.parseInt(args[0]);  
        } else {  
            port = 8080;  
        }  
        new CustomServer(port).start();  
    }  
}  