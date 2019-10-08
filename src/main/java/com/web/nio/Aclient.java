package com.web.nio;

import java.io.IOException;

public class Aclient {

    public static void main(String[] args) throws IOException {
        new NioClient().start("A");
    }
}
