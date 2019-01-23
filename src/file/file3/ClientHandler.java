package file.file3;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

//���ڶ�ȡ�ͻ��˷�������Ϣ
public class ClientHandler extends ChannelHandlerAdapter {
	private static String path = "F:\\���� - ����\\mvc";

	private static List<String> fileList = new ArrayList<String>();

	// �ͻ��������ˣ����ӳɹ����ۺ�
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// ����ͼƬ�Ĵ���
		isFile(new File(path));
		int i = 0;
		for (String filepath : fileList) {
			File file = new File(filepath);
			Request req = new Request();
			req.setName(filepath);
			req.setId(String.valueOf(++i));
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			byte[] byt = new byte[1024];
			while(in.read(byt) != -1){
				req.setAttachment(GzipUtils.gzip(byt));
				ctx.channel().writeAndFlush(req);
			}
			in.close();
			System.out.println(i+"-------cilent�����ļ��ɹ�");
		}

	}

	// ֻ�Ƕ����ݣ�û��д���ݵĻ�
	// ��Ҫ�Լ��ֶ����ͷŵ���Ϣ
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			Response response = (Response) msg;
			System.out.println(response);

		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

	private static void isFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				isFile(file2);
			}
		} else {
			fileList.add(file.getPath());
		}
	}

}
