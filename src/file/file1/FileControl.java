package file.file1;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

import java.io.RandomAccessFile;
public class FileControl {

    private ChannelHandlerContext channel;

    public FileControl(ChannelHandlerContext channel) {
        this.channel = channel;
    }

    /**
     * ֪ͨ�������Ҫ����һ���ļ���
     * �������Ϣ����Ե�һΪ�Լ���Ҫ��Ϣ��ʽ�������ļ��������ȣ����͵�
     * ����ֻ�򵥵�֪ͨ�ļ���
     */
    public void sendNotice(){
        channel.writeAndFlush("00".getBytes(CharsetUtil.UTF_8));
    }

    /**
     * �����ļ�
     * @param fileUrl
     */
    public void sendFile(String fileUrl) {
        // ��ʼִ��
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(fileUrl, "rw");
            ChunkedFile chunkedFile = new ChunkedFile(raf);
            channel.writeAndFlush(chunkedFile).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    future.channel().close();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
