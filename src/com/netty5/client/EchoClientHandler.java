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
 * 客户端发送
 * 将指定文件复制到指定位置(放压缩文件时,存放目录必须存在否则会报找不到系统文件路径)
 * 
 * @author zxy-un
 * 
 * 2018-上午9:28:02
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {
	private String context;

	public EchoClientHandler() {
		context = "testNettyText"; // 复制来文件的文件名
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		ctx.writeAndFlush(context.getBytes(CharsetUtil.UTF_8));
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		RandomAccessFile raf = null;
		try {
//			raf = new RandomAccessFile("F:\\zxy\\教案\\命令与原则(txt)\\jQuery.txt", "r"); // 被复制的文件路径
			raf = new RandomAccessFile("F:\\zxy\\TestHtml.zip", "r"); // 被复制的文件路径
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
