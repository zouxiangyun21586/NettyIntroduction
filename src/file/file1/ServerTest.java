package file.file1;
/**
 * ���������
 * @����  �����
 *
 * @ʱ�� 2017��6��15�� ����8:42:56
 */
public class ServerTest {

    public static void main(String[] args) {
        System.out.println("����NettyServer start... ...");
        Thread thread = new Thread(new NettyServer());
        thread.start();
        System.out.println("����NettyServer end... ...");
    }

}

