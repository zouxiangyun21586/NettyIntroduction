/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.netty5.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

/**
 * �ͻ��˷���
 * ��ָ���ļ����Ƶ�ָ��λ��(��ѹ���ļ�ʱ,���Ŀ¼������ڷ���ᱨ�Ҳ���ϵͳ�ļ�·��)
 * 
 * @author zxy-un
 * 
 * 2018-����9:28:02
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {
	private String context;

	public EchoClientHandler() {
		context = "testNettyText"; // �������ļ����ļ���
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		ctx.writeAndFlush(context.getBytes(CharsetUtil.UTF_8));
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		RandomAccessFile raf = null;
		try {
//			raf = new RandomAccessFile("F:\\zxy\\�̰�\\������ԭ��(txt)\\jQuery.txt", "r"); // �����Ƶ��ļ�·��
			raf = new RandomAccessFile("F:\\zxy\\TestHtml.zip", "r"); // �����Ƶ��ļ�·��
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			ctx.writeAndFlush(new ChunkedFile(raf)).addListener(new ChannelFutureListener(){
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					future.channel().close();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
