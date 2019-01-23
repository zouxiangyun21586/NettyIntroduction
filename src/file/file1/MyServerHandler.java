package file.file1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: fuzhengwei
 * Date: 15-11-8
 * Time: ����1:58
 * To change this template use File | Settings | File Templates.
 */
@ChannelHandler.Sharable
public class MyServerHandler extends ChannelHandlerAdapter {

    private boolean first = true;
    private FileOutputStream fos;
    private BufferedOutputStream bufferedOutputStream;

    /*
     * channelAction
	 *
	 * channel ͨ��
	 * action  ��Ծ��
	 *
	 * ���ͻ����������ӷ���˵����Ӻ����ͨ�����ǻ�Ծ���ˡ�Ҳ���ǿͻ��������˽�����ͨ��ͨ�����ҿ��Դ�������
	 *
	 */
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	int aa = 1;
        first = true;
        System.out.println(ctx.channel().localAddress().toString() + " channelActive");
    }

    /*
     * channelInactive
     *
     * channel 	ͨ��
     * Inactive ����Ծ��
     *
     * ���ͻ��������Ͽ�����˵����Ӻ����ͨ�����ǲ���Ծ�ġ�Ҳ����˵�ͻ��������˵Ĺر���ͨ��ͨ�����Ҳ����Դ�������
     *
     */
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	int aa = 1;
        System.out.println(ctx.channel().localAddress().toString() + " channelInactive");
        // �ر���
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        first = false;
    }

    /*
     * channelRead
     *
     * channel ͨ��
     * Read    ��
     *
     */
    public void channelRead0(ChannelHandlerContext ctx, Object msg)
            throws Exception {
    	int aa = 1;
        // ��һ�ν�����Ϣֻ�����ļ�
        if (first) {
            System.out.println("�����ļ�");
            first = false;
            File file = new File("C:\\Users\\Administrator\\Desktop\\" + new SimpleDateFormat("yyyymmddhhmmss").format(new Date()) + ".doc");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                fos = new FileOutputStream(file);
                bufferedOutputStream = new BufferedOutputStream(fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return;
        }

        // ��ʼ�����ļ���Ϣ
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        System.out.println("���ν������ݳ��ȣ�" + msg.toString().length());
        try {
            bufferedOutputStream.write(bytes, 0, bytes.length);
            buf.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * channelReadComplete
     *
     * channel  ͨ��
     * Read     ��ȡ
     * Complete ���
     *
     * ��ͨ����ȡ��ɺ�������������֪ͨ����Ӧ������ˢ�²���
     * ctx.flush()
     *
     */
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /*
     * exceptionCaught
     *
     * exception	�쳣
     * Caught		ץס
     *
     * ץס�쳣���������쳣��ʱ�򣬿�����һЩ��Ӧ�Ĵ��������ӡ��־���ر�����
     *
     */
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
        System.out.println("�쳣��Ϣ��\r\n" + cause.getMessage());
    }

}