package encoder.custom;

/**
 * ʵ����
 * @����  �����
 *
 * @ʱ�� 2017��6��14�� ����11:24:17
 */
public class CustomMsg {  
      
    //����  ϵͳ��� 0xAB ��ʾAϵͳ��0xBC ��ʾBϵͳ  
    private byte type;  
      
    //��Ϣ��־  0xAB ��ʾ������    0xBC ��ʾ��ʱ��  0xCD ҵ����Ϣ��  
    private byte flag;  
      
    //������Ϣ�ĳ���  
    private int length;  
      
    //������Ϣ  
    private String body;  
      
    public CustomMsg() {  
          
    }  
      
    public CustomMsg(byte type, byte flag, int length, String body) {  
        this.type = type;  
        this.flag = flag;  
        this.length = length;  
        this.body = body;  
    }  
  
    public byte getType() {  
        return type;  
    }  
  
    public void setType(byte type) {  
        this.type = type;  
    }  
  
    public byte getFlag() {  
        return flag;  
    }  
  
    public void setFlag(byte flag) {  
        this.flag = flag;  
    }  
  
    public int getLength() {  
        return length;  
    }  
  
    public void setLength(int length) {  
        this.length = length;  
    }  
  
    public String getBody() {  
        return body;  
    }  
  
    public void setBody(String body) {  
        this.body = body;  
    }  
  
}  