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

            b.group(group);                                //group ��
            b.channel(NioSocketChannel.class);            //channel ͨ��
            b.option(ChannelOption.TCP_NODELAY, true);  //option ѡ��
            b.handler(new ChildChannelHandlerServer());         //handler ����

            //�����첽����
            f = b.connect("127.0.0.1", 7397);

            //�ȴ��ͻ�����·�ر�
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
