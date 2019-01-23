package com.yr.encoder;

/**
 * 实体类
 * 解决粘包,半包 -- 定长解码方式
 * 
 * @author zxy-un
 * 
 * 2018-下午4:35:31
 */
public class Pojo {

	// 消息
	private String message;

	// 消息长度
	private int messageLength;

	// 主题信息的长度
	private int length;

	// 主题信息
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