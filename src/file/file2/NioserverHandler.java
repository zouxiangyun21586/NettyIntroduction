package file.file2;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;  
  
public class NioserverHandler {  
      
    private final static Logger logger = Logger.getLogger(NioserverHandler.class.getName());  
    private final static String DIRECTORY = "D:\\";  
      
    /** 
     * ��������Ǵ�����պͷ��� 
     *  
     * @param serverSocketChannel 
     */  
    public void excute(ServerSocketChannel serverSocketChannel) {  
        SocketChannel socketChannel = null;  
        try {  
            socketChannel = serverSocketChannel.accept(); // �ȴ��ͻ�������  
              
            RequestObject requestObject = receiveData(socketChannel);// ������   
            logger.log(Level.INFO,requestObject.toString());  
            writeFile(DIRECTORY,requestObject); // д�ļ�  
            String response = "File " + new String(requestObject.getFileName()) + " SUCCESS......";  
            responseData(socketChannel, response);  
            logger.log(Level.INFO, response);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * ��ȡͨ���е����ݵ�Object��ȥ 
     * @param socketChannel 
     * @return 
     * @throws IOException 
     */  
    public RequestObject receiveData(SocketChannel socketChannel) throws IOException {  
          
        // �ļ�������  
        int nameLength = 0;  
        // �ļ���  
        byte[] fileName = null;   // ����ļ����ֽ����鷢�������Ǿ��޴ӵ�֪�ļ����������ļ���һ�𷢹���
        // �ļ�����   
        int contentLength = 0;  
        // �ļ�����   
        byte[] contents = null;  
        // �������ǽ���ʱǰ4���ֽ����ļ�������  
        int capacity = 4;  
        ByteBuffer buf = ByteBuffer.allocate(capacity);  
          
        int size = 0;  
        byte[] bytes = null;  
        // �õ��ļ����ĳ���  
        size = socketChannel.read(buf);  
        if (size >= 0) {  
            buf.flip();  
            capacity = buf.getInt();  
            buf.clear();  
            nameLength = capacity;  
        }  
          
        // ���ļ���,�����ļ���һ���ܹ�����,������ļ�������1k
        buf = ByteBuffer.allocate(capacity);  
        size = socketChannel.read(buf);  
        if (size >= 0) {  
            buf.flip();  
            bytes = new byte[size];  
            buf.get(bytes);  
            fileName = bytes;  
            buf.clear();  
        }  
          
        // �õ��ļ�����  
        capacity = 4;  
        buf = ByteBuffer.allocate(capacity);  
        size = socketChannel.read(buf);  
        if (size >= 0) {  
            buf.flip();  
            // �ļ������ǿ�Ҫ�ɲ�Ҫ�ģ������Ҫ��У���������  
            contentLength = buf.getInt();  
            buf.clear();  
        }  
          
        // ���ڽ���buffer�е��ֽ�����  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        // �ļ����ܻ�ܴ�  
        capacity = 1024;  
        buf = ByteBuffer.allocate(capacity);  
        while((size = socketChannel.read(buf)) >= 0){  
            buf.flip();  
            bytes = new byte[size];  
            buf.get(bytes);  
            baos.write(bytes);  
            buf.clear();  
        }  
        contents = baos.toByteArray();  
        RequestObject requestObject = new RequestObject(nameLength, fileName, contentLength, contents);  
          
        return requestObject;  
    }  
      
    /** 
     * �ѽ��յ�����д�������ļ��� 
     * @param drc �����ļ�Ŀ¼ 
     * @param requestObject 
     * @throws UnsupportedEncodingException  
     * @throws IOException  
     */  
    public static void writeFile(String drc,RequestObject requestObject) throws UnsupportedEncodingException {  
        File file = new File(drc + File.separator + new String(requestObject.getFileName(),"utf-8"));  
        FileOutputStream os = null;  
        try {  
            os = new FileOutputStream(file);  
            os.write(requestObject.getContents());  
            os.flush();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (os != null)  
                    os.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
      
    /** 
     * ���ǽ������ݳɹ���Ҫ���ͻ���һ������ 
     * �����java�Ŀͻ������ǿ���ֱ�Ӹ�����,���������û��Ǹ��ı����ֽ����� 
     * @param socketChannel 
     * @param response 
     */  
    private void responseData(SocketChannel socketChannel, String response) {  
        ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());  
        try {  
            socketChannel.write(buffer);  
            buffer.clear();  
            // ȷ��Ҫ���͵Ķ����������˹ر�output ��Ȼ���˽���ʱsocketChannel.read(Buffer)  
            // �ܿ���������� �����԰������L��ע�͵����ᷢ�ֿͻ���һֱ�ȴ���������  
            socketChannel.socket().shutdownOutput();  // ��L��  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
      
}  