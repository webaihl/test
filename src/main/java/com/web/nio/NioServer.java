package com.web.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class NioServer {

    public static void main(String[] args) throws IOException {
        new NioServer().start();
    }

    public void start() throws IOException {

        // 1、创建selector
        Selector selector = Selector.open();
        // 2、创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 3、serverSocketChannel 绑定端口号
        serverSocketChannel.bind(new InetSocketAddress(8000));
        // 4、设置为非阻塞类型
        serverSocketChannel.configureBlocking(false);
        //5、注册到selector,监听连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务端启动成功！");
        //6、等待客户端的连接
        for (; ; ) {
            //可用的channel连接的数量
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

                //根据就绪状态处理业务逻辑
                //接入事件
                if (selectionKey.isAcceptable()) {
                    acceptHandle(serverSocketChannel, selector);
                }
                //可读事件
                if (selectionKey.isReadable()) {
                    readHandle(selectionKey, selector);
                }
            }

        }
    }

    private void acceptHandle(ServerSocketChannel serverSocketChannel, Selector selector) throws IOException {

        // 创建socketChannel
        SocketChannel socketChannel = serverSocketChannel.accept();
        //设置非阻塞模式
        socketChannel.configureBlocking(false);
        //注册到selector， 监听可读事件
        socketChannel.register(selector, SelectionKey.OP_READ);
        //回复客户端提示消息
        socketChannel.write(Charset.forName("UTF-8").encode("你已经加入聊天室，请注意隐私安全！"));
    }

    private void readHandle(SelectionKey selectionKey, Selector selector) throws IOException {
        //selectionKey中获取就绪的channel
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        //为读写操作定义buffer对象
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //循环读出数据
        String request = "";
        while (socketChannel.read(buffer) > 0) {
            //切换成读模式
            buffer.flip();
            //读取数据
            request += Charset.forName("UTF-8").decode(buffer);
        }
        //重新注册到selector，监听其他的read事件
        socketChannel.register(selector, SelectionKey.OP_READ);
        //广播给其他用户
        if (request.length() > 0) {
            System.out.println(request);
            broadCast(selector, socketChannel, request);
        }
    }

    private void broadCast(Selector selector, SocketChannel sourceChannel, String request) {
        //获取所有已经接入的channel，不一定是就绪
        Set<SelectionKey> selectionKeys = selector.keys();
        if (selectionKeys.size() > 0) {
            //循环向channel发送广播信息
            selectionKeys.forEach(selectionKey -> {
                Channel targetChannel = selectionKey.channel();
                //排序信息发送者
                if (targetChannel instanceof SocketChannel && targetChannel != sourceChannel) {
                    try {
                        ((SocketChannel) targetChannel).write(Charset.forName("UTF-8").encode(request));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
