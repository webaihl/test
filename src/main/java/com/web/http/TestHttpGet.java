package com.web.http;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/*import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;*/


public class TestHttpGet extends Thread {

    public static void main(String[] args) {
        new TestHttpGet().start();
    }

    @Override
    public void run() {
		/*
		try {
			URL url = new URL(
					"http://fanyi.youdao.com/openapi.do?keyfrom=web1213&key=1222314327&type=data&doctype=json&version=1.1&q=word"); // 建立URL对象
			URLConnection connection = url.openConnection();// 打开URL
			InputStream is = connection.getInputStream(); // 获取网络数据
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");// 手动指示编码
			BufferedReader br = new BufferedReader(isr);

			String line;
			StringBuffer sb = new StringBuffer(); // 非线程安全
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			isr.close();
			is.close();
			System.out.println(sb.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	*/

        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建httpGet请求
        HttpGet httpGet = new HttpGet(
                "http://fanyi.youdao.com/openapi.do?keyfrom=web1213&key=1222314327&type=data&doctype=json&version=1.1&q=word");
        try {
            // 执行get请求，获取返回的详细信息
            CloseableHttpResponse response = httpClient.execute(httpGet);
            //响应状态
            System.out.println("Status: " + response.getStatusLine());
            //头数据信息
            Header[] headers = response.getHeaders("Server");
            if (headers != null && headers.length > 0) {
                System.out.println("Server: " + response.getHeaders("Server")[0].getValue());
            }
            //获取返回的数据
            HttpEntity entity = response.getEntity();
            System.out.println(EntityUtils.toString(entity));

            response.close();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
