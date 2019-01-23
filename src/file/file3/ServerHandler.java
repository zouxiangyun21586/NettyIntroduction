package file.file3;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {
	// ���ڻ�ȡ�ͻ��˷��͵���Ϣ
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		Request req = (Request) msg;
		System.out.println("Server : " + req.getId() + ", " + req.getName());
		// �����ļ���Դ�Ļ�ԭ
		byte[] attachment = GzipUtils.ungzip(req.getAttachment());

		// ��ȡ�ļ��ı���Ŀ¼
		String path = req.getName().replace("F", "D");
		System.out.println("�½��ļ���·����" + path);
		// �����ļ��ı���
		File file = new File(path);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			file.createNewFile();
		}
		try {
			@SuppressWarnings("resource")
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file, true));
			System.err.println("����д���ֽ�����" + attachment.length);
			out.write(attachment, 0, attachment.length);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ���ͻ��ˣ���Ӧ����
		Response resp = new Response();
		resp.setId(req.getId());
		resp.setName("resp" + req.getId());
		resp.setResponseMessage("��Ӧ����" + req.getId());
		ctx.writeAndFlush(resp);// .addListener(ChannelFutureListener.CLOSE);

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// cause.printStackTrace();
		ctx.close();
	}
}