package com.yr.encoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;  
  
/**
 * �ͻ���
 * ���ճ��,��� -- �������뷽ʽ 
 * 
 * ճ��: ǰ����Ϣ������һ��(eg: abc abc  --  abca bc)
 * ���: ֻ���յ�һ������Ϣ(eg: abcdef  --  abcd)
 * ���: ����Ĳ���
 * 
 * @author zxy-un
 * 
 * 2018-����4:31:06
 */
public class Client {  
      
    static final String HOST = System.getProperty("host", "127.0.0.1"); // ����ip,ֻ��д�Լ��ı�����ip�������Ӳ��� (���ڷ���˻�ȡ����˭��������Ϣ)
    static final int PORT = Integer.parseInt(System.getProperty("port", "8866")); // ʹ�ö˿�  
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));  
  
    public static void main(String[] args) throws Exception {  
  
        EventLoopGroup group = new NioEventLoopGroup();  
        try {  
            Bootstrap b = new Bootstrap();  
            b.group(group)  
             .channel(NioSocketChannel.class)  
             .option(ChannelOption.TCP_NODELAY, true)  
             .handler(new ChannelInitializer<SocketChannel>() {  // �ܵ�
                 @Override  
                 public void initChannel(SocketChannel ch) throws Exception {  
                     ch.pipeline().addLast(new Encoder());  // ���б���
                     ch.pipeline().addLast(new ClientHandler());  
                 }  
             });  
  
            ChannelFuture future = b.connect(HOST, PORT).sync();  
            future.channel().writeAndFlush("Hello Netty Server ,I am a common client");  
            future.channel().closeFuture().sync();  
        } finally {  
            group.shutdownGracefully();  
        }  
    }  
  
}  