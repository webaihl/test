package com.web.http;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class TestHttpPost implements Runnable {

    public static void main(String[] args) {

        TestHttpPost testPost = new TestHttpPost();
        new Thread(testPost).start();
    }

    @Override
    public void run() {
		/*

		try {
			URL url = new URL("http://fanyi.youdao.com/openapi.do");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.addRequestProperty("encoding", "UTF-8");  //设置编码
			connection.setRequestProperty("contentType", "utf-8");
			connection.setDoOutput(true);  //设置可输出
			connection.setDoInput(true);   //   可读写
			connection.setRequestMethod("POST");  //先设置访问方式、再操作

	      java.io.OutputStream os = connection.getOutputStream();
	      OutputStreamWriter osw = new OutputStreamWriter(os,"UTF-8");
	      BufferedWriter bw = new BufferedWriter(osw);

	      bw.write("keyfrom=web1213&key=1222314327&type=data&doctype=json&version=1.1&q=世界");  //查询后缀
	      bw.flush();

	      //查询后再读取
	      InputStream is = connection.getInputStream();
	      InputStreamReader isr = new InputStreamReader(is);
	      BufferedReader br = new BufferedReader(isr);

	      String line;
	      StringBuilder sb = new StringBuilder();

	      while ((line = br.readLine()) != null) {

	    	  sb.append(line);

		}

	      br.close();
	      isr.close();
	      is.close();

	      bw.close();
	      osw.close();
	      os.close();

		System.out.println(sb.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	*/
        // 获取httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //建立httpPost
        HttpPost httpPost = new HttpPost("http://fanyi.youdao.com/openapi.do");
        //组装httpPost的请求参数，必须是NameValuePair对象的
        ArrayList<NameValuePair> form = new ArrayList<NameValuePair>();
        form.add(new BasicNameValuePair("keyfrom", "web1213"));
        form.add(new BasicNameValuePair("key", "1222314327"));
        form.add(new BasicNameValuePair("type", "data"));
        form.add(new BasicNameValuePair("version", "1.1"));
        form.add(new BasicNameValuePair("q", "你好，世界"));
        form.add(new BasicNameValuePair("doctype", "json"));

        try {

            //按指定的字符集格式化参数
            UrlEncodedFormEntity uFormEntity = new UrlEncodedFormEntity(form, "UTF-8");
            //为Post请求设置参数
            httpPost.setEntity(uFormEntity);

            //执行请求，获取返回的信息
            CloseableHttpResponse response = httpClient.execute(httpPost);
            //数据信息
            HttpEntity entity = response.getEntity();
            System.out.println(EntityUtils.toString(entity));

            System.out.println(response.getLocale());

            response.close();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
