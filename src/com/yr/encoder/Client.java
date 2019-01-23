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
 * 客户端
 * 解决粘包,半包 -- 定长解码方式 
 * 
 * 粘包: 前后信息连接在一起(eg: abc abc  --  abca bc)
 * 半包: 只接收到一部分信息(eg: abcdef  --  abcd)
 * 拆包: 处理的部分
 * 
 * @author zxy-un
 * 
 * 2018-下午4:31:06
 */
public class Client {  
      
    static final String HOST = System.getProperty("host", "127.0.0.1"); // 本机ip,只能写自己的本机的ip否则连接不上 (可在服务端获取到是谁发来的信息)
    static final int PORT = Integer.parseInt(System.getProperty("port", "8866")); // 使用端口  
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));  
  
    public static void main(String[] args) throws Exception {  
  
        EventLoopGroup group = new NioEventLoopGroup();  
        try {  
            Bootstrap b = new Bootstrap();  
            b.group(group)  
             .channel(NioSocketChannel.class)  
             .option(ChannelOption.TCP_NODELAY, true)  
             .handler(new ChannelInitializer<SocketChannel>() {  // 管道
                 @Override  
                 public void initChannel(SocketChannel ch) throws Exception {  
                     ch.pipeline().addLast(new Encoder());  // 进行编码
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