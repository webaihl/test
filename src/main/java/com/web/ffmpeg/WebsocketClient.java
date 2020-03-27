package com.web.ffmpeg;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WebsocketClient extends WebSocketClient {

    private List<byte[]> masByteList = new ArrayList<>();

    @Getter private volatile String response;

    public WebsocketClient(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }
    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("开始建立链接...");
    }

    @Override
    public void onMessage(String message) {
        log.info("检测到服务器请求...message： " + message);
        this.response = message;
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.info("客户端已关闭!");
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
        log.info("客户端发生错误,即将关闭!");
    }

    @Override
    public void onMessage(ByteBuffer byteBuffer) {
        byte[] bytes = convert(byteBuffer);
        log.info("获取视频流长度："+bytes.length);
        masByteList.add(bytes);
    }

    public static byte[] convert(ByteBuffer byteBuffer){
        int len = byteBuffer.limit() - byteBuffer.position();
        byte[] bytes = new byte[len];

        if(byteBuffer.isReadOnly()){
            return null;
        }else {
            byteBuffer.get(bytes);
        }
        return bytes;
    }

    public List<byte[]> getMasByteList() {
        return masByteList;
    }

}
