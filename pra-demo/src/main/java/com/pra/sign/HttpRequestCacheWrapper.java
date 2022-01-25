package com.pra.sign;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author wanzicong
 */
public class HttpRequestCacheWrapper extends HttpServletRequestWrapper {

    private final byte[] body;
    /**
     * 构造函数
     */
    public HttpRequestCacheWrapper(HttpServletRequest request) {
        super(request);
        String body = getBodyString(request);
        this.body = body.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 根据requst 对象
     * @param request 请求对象
     * @return 返回信息
     */
    private String getBodyString(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();

        try() {
            InputStream inputStream = request.getInputStream();
            inputStream = cloneInputStream(inputStream);
            BufferedReader reader = new BufferedReader(inputStream,StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 克隆 inputStream
     * @param inputStream 请求流数据
     * @return 返回信息
     */
    private InputStream cloneInputStream(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len;
        byte[] bytes = new byte[1024];
        try{
            while((len = inputStream.read(bytes)) > -1 ) {
                byteArrayOutputStream.write(bytes,0,len);
            }
            byteArrayOutputStream.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }




}
