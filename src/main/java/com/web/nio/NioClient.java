package com.web.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class NioClient {

    public static void main(String[] args) throws IOException {
        // new NioClient().start();
    }

    public void start(String nickname) throws IOException {
        //连接服务器
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8000));

        //接受服务器的数据响应
        //另开线程
        Selector selector = Selector.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        new Thread(new NioClientHandle(selector)).start();

        //向服务器发送信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String res = scanner.nextLine();
            if (res != null && res.length() > 0) {
                socketChannel.write(Charset.forName("UTF-8").encode(nickname + ": " + res));
            }
        }

    }
}
