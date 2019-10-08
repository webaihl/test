package com.web.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class NioClientHandle implements Runnable {
    private Selector selector;

    public NioClientHandle(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            for (; ; ) {
                int readyChannels = selector.select();
                if (readyChannels == 0) continue;

                //可用的channel连接的集合
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    //selectionKey实例
                    SelectionKey selectionKey = iterator.next();

                    //移除当前的selectionKey实例，防止Set集合过大
                    iterator.remove();

                    //可读事件
                    if (selectionKey.isReadable()) {
                        readHandle(selectionKey, selector);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readHandle(SelectionKey selectionKey, Selector selector) throws IOException {
        //selectionKey中获取就绪的channel
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        //为读写操作定义buffer对象
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //循环读出数据
        String response = "";
        while (socketChannel.read(buffer) > 0) {
            //切换成读模式
            buffer.flip();
            //读取数据
            response += Charset.forName("UTF-8").decode(buffer);
        }
        socketChannel.register(selector, SelectionKey.OP_READ);
        //输出服务端发送的数据
        if (response.length() > 0) {
            System.out.println(response);
        }
    }
}
