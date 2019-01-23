package file.file1;

import java.util.Date;
/**
 * 客户端
 * @作者  罗玲红
 *
 * @时间 2017年6月15日 上午8:42:26
 */
public class ClientTest {
	  public static void main(String[] args) {
	        System.out.println("启动NettyClient start ... ...");
	        Thread thread = new Thread(new NettyClient());
	        thread.start();
	        System.out.println("启动NettyClient end ... ...");

	        // 当socket链接上才可以进行传送文件
	        while (null == Globle.channel) {
	            try {
	                Thread.sleep(1000);
	                System.out.println("等待socket握手... ...");
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }

	        System.out.println("发送文件 开始" + new Date());
	        String fileUrl = "G:\\xm\\项目总结.doc";
	        FileControl fileControl = new FileControl(Globle.channel);
	        // 通知服务端我要发文件了
	        fileControl.sendNotice();
	        // 开始发文件
	        fileControl.sendFile(fileUrl);
	        System.out.println("发送文件 结束" + new Date());
	    }
}
