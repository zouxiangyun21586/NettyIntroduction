package file.file1;
/**
 * 服务端启动
 * @作者  罗玲红
 *
 * @时间 2017年6月15日 上午8:42:56
 */
public class ServerTest {

    public static void main(String[] args) {
        System.out.println("启动NettyServer start... ...");
        Thread thread = new Thread(new NettyServer());
        thread.start();
        System.out.println("启动NettyServer end... ...");
    }

}

