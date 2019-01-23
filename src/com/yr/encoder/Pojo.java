package com.yr.encoder;

/**
 * ʵ����
 * ���ճ��,��� -- �������뷽ʽ
 * 
 * @author zxy-un
 * 
 * 2018-����4:35:31
 */
public class Pojo {

	// ��Ϣ
	private String message;

	// ��Ϣ����
	private int messageLength;

	// ������Ϣ�ĳ���
	private int length;

	// ������Ϣ
	private String body;

	public Pojo() {

	}

	public Pojo(String message, int messageLength, int length, String body) {
		this.message = message;
		this.messageLength = messageLength;
		this.length = length;
		this.body = body;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getMessageLength() {
		return messageLength;
	}

	public void setMessageLength(int messageLength) {
		this.messageLength = messageLength;
	}

	@Override
	public String toString() {
		return "EncoderMsg [message=" + message + ", messageLength=" + messageLength + ", length=" + length + ", body="
				+ body + "]";
	}

}