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
package com.netty5.server;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 服务端通道
 * 将指定文件复制到指定位置
 * 
 * @author zxy-un
 * 
 * 2018-上午9:26:19
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	private boolean first = true;
	private FileOutputStream fos;
	private BufferedOutputStream bufferedOutputStream;
	private static String prefix = "C:\\Users\\Administrator\\Desktop\\"; // 文件路径后必加斜杠,否则会在上级目录中保存
	private static String subfix = ".zip"; // 后缀
	
	private String OK = "ok";
	private String ERR = "err";
	
    @Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf buf = (ByteBuf) msg;
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		if (first) {
			String fileName = new String(bytes);
			System.out.println(fileName);
			first = false;
			File file = new File(prefix + fileName + subfix); // 文件需要加上后缀
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				fos =  new FileOutputStream(file);
				bufferedOutputStream = new BufferedOutputStream(fos);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			ctx.writeAndFlush(OK.getBytes());
			buf.release();
			return;
		}
		
    	try {
    		bufferedOutputStream.write(bytes, 0, bytes.length);
    		buf.release();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		bufferedOutputStream.flush();
		bufferedOutputStream.close();
	}
	
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
    	ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
