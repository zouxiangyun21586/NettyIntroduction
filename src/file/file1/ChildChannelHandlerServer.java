package file.file1;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-11-8
 * Time: 下午1:57
 */
public class ChildChannelHandlerServer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
    	  System.out.println("����");
          System.out.println("��Ϣ����һ�ͻ������ӵ��������");
          System.out.println("IP:" + ch.localAddress().getHostName());
          System.out.println("Port:" + ch.localAddress().getPort());
          System.out.println("�������");
          ch.pipeline().addLast(new ByteArrayEncoder());
          // �ڹܵ�����������Լ��Ľ�������ʵ�ַ���
          ch.pipeline().addLast(new ChildChannelHandlerServer());

    }
}
