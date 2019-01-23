package file;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * 客户端
 * 生成压缩文件
 * 
 * @author zxy-un
 * 
 * 2018-上午9:53:17
 */
public class FileClient {
	private int port = 8000;
	/* 发送数据缓冲区 */
	private static ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
	private InetSocketAddress SERVER;
	private static Selector selector;
	private static SocketChannel client;
	
	public FileClient(){
		try{
			SERVER = new InetSocketAddress("192.168.1.86", port);
			init();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	private void init(){
		try {
			SocketChannel socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			selector = Selector.open();
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
			socketChannel.connect(SERVER);
			while (true) {
				selector.select();
				Set<SelectionKey> keySet = selector.selectedKeys();
				for (final SelectionKey key : keySet) {
					if(key.isConnectable()){
						client = (SocketChannel)key.channel();
						client.finishConnect();
						client.register(selector, SelectionKey.OP_WRITE);

					}
					else if(key.isWritable()){
						sendFile(client);
					}
				}
				keySet.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendFile(SocketChannel client) {
		FileInputStream fis = null;
		FileChannel channel = null;
		try {
			fis = new FileInputStream("F:\\zxy\\TestHtml.zip"); // 压缩文件所在位置
//			fis = new FileInputStream("F:\\zxy\\教案\\命令与原则(txt)\\jQuery.txt"); // 文件所在位置
			channel = fis.getChannel();
			int i = 1;
			int count = 0;
			while((count = channel.read(sendBuffer)) != -1) {
				sendBuffer.flip(); 
				int send = client.write(sendBuffer);
				System.out.println("i===========" + (i++) + "   count:" + count + " send:" + send);
				// 服务器端可能因为缓存区满，而导致数据传输失败，需要重新发送
				while(send == 0){
					Thread.sleep(10);
					send = client.write(sendBuffer);
					System.out.println("i重新传输====" + i + "   count:" + count + " send:" + send);
				}
				sendBuffer.clear(); 
           }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				channel.close();
				fis.close();
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	
	public static void main(String[] args){
		new FileClient();
	}
	
}