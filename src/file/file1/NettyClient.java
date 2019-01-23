package file.file1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient implements Runnable {
    private ChannelFuture f = null;


    @Override
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();

        try {

            Bootstrap b = new Bootstrap();

            b.group(group);                                //group 组
            b.channel(NioSocketChannel.class);            //channel 通道
            b.option(ChannelOption.TCP_NODELAY, true);  //option 选项
            b.handler(new ChildChannelHandlerServer());         //handler 处理

            //发起异步链接
            f = b.connect("127.0.0.1", 7397);

            //等待客户端链路关闭
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
