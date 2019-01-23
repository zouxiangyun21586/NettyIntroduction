package file.file1;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ChildChannelHandlerClient  extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new ByteArrayEncoder());
        ch.pipeline().addLast(new ChunkedWriteHandler());
        ch.pipeline().addLast(new MyClientHandler());
    }
}
