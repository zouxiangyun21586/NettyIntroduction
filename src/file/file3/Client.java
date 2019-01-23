package file.file3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

	/**
	 * ���ӷ�����
	 * 
	 * @param port
	 * @param host
	 * @throws Exception
	 */
	public void connect(int port, String host) throws Exception {
		// ���ÿͻ���NIO�߳���
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			// �ͻ��˸��������� �Կͻ�������
			Bootstrap b = new Bootstrap();
			b.group(group)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new MyChannelHandler());
			// �첽���ӷ����� ͬ���ȴ����ӳɹ�
			ChannelFuture f = b.connect(host, port).sync();

			// �ȴ����ӹر�
			f.channel().closeFuture().sync();

		} finally {
			group.shutdownGracefully();
			System.out.println("�ͻ������ŵ��ͷ����߳���Դ...");
		}

	}

	/**
	 * �����¼�������
	 */
	private class MyChannelHandler extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			// ���Jboss�����л�������빤��
			ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
			ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
			// ��������IO
			ch.pipeline().addLast(new ClientHandler());// ��������IO
		}

	}

	public static void main(String[] args) throws Exception {
		new Client().connect(9999, "127.0.0.1");

	}

}
