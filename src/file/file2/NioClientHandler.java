package file.file2;
import java.io.ByteArrayOutputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.nio.ByteBuffer;  
import java.nio.channels.SocketChannel;  
  
public class NioClientHandler {  
    /** 
     * ��������������� 
     *  
     * @param socketChannel 
     * @param filepath  �ļ�·��
     * @param filename  �ļ���
     * @throws IOException 
     */  
    public void sendData(SocketChannel socketChannel,String filepath, String filename) throws IOException {  
        byte[] bytes = makeFileToByte(filepath);  
        // ByteBuffer�Ļ��������ȣ�4�ֽڵ��ļ�������+�ļ���+4�ֽ��ļ�����+�ļ�  
        ByteBuffer buffer = ByteBuffer.allocate(8 + filename.getBytes().length + bytes.length);  
        buffer.putInt(filename.length()); // �ļ�������  
        buffer.put(filename.getBytes());  // �ļ���  
        buffer.putInt(bytes.length);     // �ļ�����  
        buffer.put(ByteBuffer.wrap(bytes));// �ļ�  
        buffer.flip();    // �ѻ������Ķ�λָ��ʼ0��λ�� ������б��  
        socketChannel.write(buffer);  
        buffer.clear();  
        // �ر��������ֹ����ʱ������ ���Ǹ��߽��շ����ε������Ѿ������ˣ��㲻�õ���  
        socketChannel.socket().shutdownOutput();  
    }  
      
    /** 
     * ���շ�������Ӧ����Ϣ 
     * @param socketChannel 
     * @return 
     * @throws IOException 
     */  
    public String receiveData(SocketChannel socketChannel)  
            throws IOException {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        String response = "";  
        try {  
            ByteBuffer buffer = ByteBuffer.allocate(1024);  
            byte[] bytes;  
            int count = 0;  
            while ((count = socketChannel.read(buffer)) >= 0) {  
                buffer.flip();  
                bytes = new byte[count];  
                buffer.get(bytes);  
                baos.write(bytes);  
                buffer.clear();  
            }  
              
            bytes = baos.toByteArray();  
            response = new String(bytes,"UTF-8");  
            socketChannel.socket().shutdownInput();  
        } finally {  
            try {  
                baos.close();  
            } catch (Exception ex) {  
            }  
        }  
        return response;  
    }  
  
    /**
     * ���ļ�ת����byte
     * @param fileFath
     * @return
     * @throws IOException
     */
    private byte[] makeFileToByte(String fileFath) throws IOException {  
        File file = new File(fileFath);  
        FileInputStream fis = new FileInputStream(file);  
        int length = (int) file.length();  
        byte[] bytes = new byte[length];  
        int temp = 0;  
        int index = 0;  
        while(true){  
            index = fis.read(bytes,temp,length - temp);  
            if(index <= 0 )  
                break;  
            temp += index;  
        }  
        fis.close();  
        return bytes;  
    }  
      
}  