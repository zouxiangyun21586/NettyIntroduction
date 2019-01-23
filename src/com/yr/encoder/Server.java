package com.yr.encoder;
import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;  
  
/**
 * �����
 * ���ճ��,��� -- �������뷽ʽ
 * 
 * @author zxy-un
 * 
 * 2018-����8:18:41
 */
public class Server {  

    private int port;  // �˿�
      
    public Server(int port) {  
        this.port = port;  
    }  
      
    public void start(){  
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        try {  
            ServerBootstrap sbs = new ServerBootstrap().group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))  
                    .childHandler(new ChannelInitializer<SocketChannel>() {  
                          
                        protected void initChannel(SocketChannel ch) throws Exception {  
                        	/**
                        	 * ��������:
                        	 * 	1) ����ÿ��֡���ݵ���󳤶�  (MAX_FRAME_LENGTH)
                        	 * 	2) ��¼��֡���ݳ��ȵ��ֶα���ĳ���  (LENGTH_FIELD_LENGTH)
                        	 * 	3) ��֡�����У���Ÿ�֡���ݵĳ��ȵ����ݵ���ʼλ��  (LENGTH_FIELD_OFFSET)
                        	 * 	4) �޸�֡���ݳ����ֶ��ж����ֵ������Ϊ���� (LENGTH_ADJUSTMENT)
                        	 * 	5) ������ʱ����Ҫ�������ֽ���  (INITIAL_BYTES_TO_STRIP)
                        	 * 	6) Ϊtrue����frame���ȳ���maxFrameLengthʱ������TooLongFrameException�쳣��
                    	 	 *     Ϊfalse����ȡ������֡�ٱ��쳣 (failFast)
                        	 */
                        	ch.pipeline().addLast(new Decoder(1024*1024, 0, 0, 0, 0, true)); // ���н���
                            ch.pipeline().addLast(new ServerHandler());  
                        };  
                          
                    }).option(ChannelOption.SO_BACKLOG, 128)     
                    .childOption(ChannelOption.SO_KEEPALIVE, true);  
             // �󶨶˿ڣ���ʼ���ս���������  
             ChannelFuture future = sbs.bind(port).sync();    
               
             System.out.println("����ʼ������   " + port + " �˿�" );  
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
            port = 8866;  
        }  
        new Server(port).start();  
    }  
}  