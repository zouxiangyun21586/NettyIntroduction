package file.file1;

import java.util.Date;
/**
 * �ͻ���
 * @����  �����
 *
 * @ʱ�� 2017��6��15�� ����8:42:26
 */
public class ClientTest {
	  public static void main(String[] args) {
	        System.out.println("����NettyClient start ... ...");
	        Thread thread = new Thread(new NettyClient());
	        thread.start();
	        System.out.println("����NettyClient end ... ...");

	        // ��socket�����ϲſ��Խ��д����ļ�
	        while (null == Globle.channel) {
	            try {
	                Thread.sleep(1000);
	                System.out.println("�ȴ�socket����... ...");
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }

	        System.out.println("�����ļ� ��ʼ" + new Date());
	        String fileUrl = "G:\\xm\\��Ŀ�ܽ�.doc";
	        FileControl fileControl = new FileControl(Globle.channel);
	        // ֪ͨ�������Ҫ���ļ���
	        fileControl.sendNotice();
	        // ��ʼ���ļ�
	        fileControl.sendFile(fileUrl);
	        System.out.println("�����ļ� ����" + new Date());
	    }
}
