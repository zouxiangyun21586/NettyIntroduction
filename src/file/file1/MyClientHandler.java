package file.file1;

import java.util.Date;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created with IntelliJ IDEA.
 * User: fuzhengwei
 * Date: 15-11-8
 * Time: ����2:34
 * To change this template use File | Settings | File Templates.
 */
public class MyClientHandler extends ChannelHandlerAdapter {

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
        Globle.channel = ctx;
        System.out.println("�ͻ���������ͨ��-������" + ctx.channel().localAddress() + "channelActive");
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
        Globle.channel = null;
        System.out.println("�ͻ���������ͨ��-�رգ�" + ctx.channel().localAddress() + "channelInactive");
    }

    /*
     * channelRead
     *
     * channel ͨ��
     * Read    ��
     *
     * �����֮���Ǵ�ͨ���ж�ȡ���ݣ�Ҳ���Ƿ���˽��տͻ��˷���������
     * ������������ڲ����н���ʱ����ByteBuf���͵ĺ������������ڽ���
     *
     */
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println(ctx.channel().id() + "" + new Date() + " " + msg);
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
        Globle.channel = null;
        ctx.close();
        System.out.println("�쳣�˳�:" + cause.getMessage());
    }

}
