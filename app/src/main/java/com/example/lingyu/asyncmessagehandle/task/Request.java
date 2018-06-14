package com.example.lingyu.asyncmessagehandle.task;

import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.example.lingyu.asyncmessagehandle.utils.CounterOutputStream;
import com.example.lingyu.asyncmessagehandle.utils.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by duanlingyu on 2018/6/11.
 * 请求体
 */

public class Request {
    /**
     * 请求头中的分隔符
     */
    private String boundary = createBoundary();

    private String startBoundry = "--"+boundary;

    private String endBoundary = startBoundry + "--";
    /**
     * 默认的字符编码集
     */
    private String charset = "utf-8";
    /**
     * 请求的地址
     */
    private String url;
    /**
     * 请求的方法
     */
    private RequestMethod method;
    /**
     * 请求的参数
     */
    private List<Params> paramList;

    /**
     * ssl连接验证
     */
    private SSLSocketFactory factory;
    /**
     * 主机验证
     */
    private HostnameVerifier verifier;
    /**
     * 请求头
     */
    private Map<String,String> mRequestHeader;
    /**
     * 请求的数据类型
     */
    private String contentType;
    /**
     * 是否强制开启表单提交
     */
    private boolean isFormData = false;


    public Request(String url) {
        this(url,RequestMethod.GET);
    }

    public Request(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
        paramList = new ArrayList<>();
        mRequestHeader = new HashMap<>();
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * 增加参数
     * @param key
     * @param value
     */
    public void add(String key,String value){

        paramList.add(new Params(key,value));

    }

    public void add(String key,File value){

        paramList.add(new Params(key,value));

    }

    /**
     * 获取请求头
     * @return
     */
    Map<String, String> getmRequestHeader() {

        return mRequestHeader;
    }

    /**
     * 设置请求头
     * @param key
     * @param value
     */
    public void setmRequestHeader(String key,String value) {

        mRequestHeader.put(key,value);
    }

    /**
     * 拿到完整的url
     * @return
     */
    public String getUrl() throws IOException{
        StringBuilder builder = new StringBuilder(url);
        String parmas = buildParams();

        if(!method.isOutputMethod()){
            if(parmas.length()>0 && url.contains("?") && url.contains("=")){
                // http://www.baidu.com?name="zhangsan"
                builder.append("&");
            }else if(parmas.length()>0 && !url.endsWith("?")){
                builder.append("?");
            }
            builder.append(parmas);
        }
        return builder.toString();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEndBoundary() {
        return endBoundary;
    }

    /**
     * 获取请求方法
     * @return
     */
    public RequestMethod getMethod() {
        return method;
    }

    /**
     * 设置请求方法
     * @param method
     */
    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public SSLSocketFactory getFactory() {
        return factory;
    }

    /**
     * 设置 SSLSocketFactory
     * @param factory {@link SSLSocketFactory}
     */
    public void setFactory(SSLSocketFactory factory) {
        this.factory = factory;
    }

    public HostnameVerifier getVerifier() {
        return verifier;
    }

    /**
     * 设置 HostnameVerifier
     * @param verifier {@link HostnameVerifier}
     */
    public void setVerifier(HostnameVerifier verifier) {
        this.verifier = verifier;
    }

    /**
     * 获取请求的数据类型
     * @return
     */
    public String getContentType() {
        if(TextUtils.isEmpty(contentType)){
            return "application/x-www-form-urlencoded";
        }else if(isFormData || hasFile()){         //是否是表单提交或者参数是文件（只能通过表单或者body来提交）
            //表单提交的特殊ContentType
            // ContentType:multipart/form-data boundary=dfdsafkdsjlkfs
            // 表单中的String item==========
            // --boundary                                           //会在每个item前添加开始分隔符
            // Content-Disposition: form-data; name="keyName"       //相当于key=value中的key
            // Content-Type: text/plain charset: "utf-8"
            //
            // String的数据...
            // 表单中的File item========
            // --boundary
            // Content-Disposition: form-data; name="keyName"; fileName="abc.jpg"       //相当于key=value中的key
            // Content-Type: image/jpeg;
            //
            // file 的数据...
            //boundary--                                            //会在所有表单提交完成添加结束分隔符
            return "multipart/form-data; boundary=" + boundary;
        }else{
            return "application/x-www-form-urlencoded";
        }


    }

    /**
     * 获取contentlength
     * @return
     */
    public long getContentLength(){

        CounterOutputStream outputStream = new CounterOutputStream();
        try {
            writeBody(outputStream);
        } catch (IOException e) {
            return 0;
        }
        return outputStream.getLength();

    }

    public void writeBody(OutputStream outputStream) throws IOException{
        if(!isFormData && !hasFile()){
            writeStringData(outputStream);
        }else if(isFormData || hasFile()){
            writeFormData(outputStream);
        }
    }

    /**
     * 写入普通类型的String数据
     * @param outputStream
     */
    private void writeStringData(OutputStream outputStream) throws IOException{
        String params = buildParams();
        Logger.I("writeStringData" + params);
        outputStream.write(params.getBytes());
    }

    /**
     * 写入表单类型的输入
     * @param outputStream
     */
    private void writeFormData(OutputStream outputStream) throws IOException{
        for (Params params : paramList) {
            if(params.getValue() instanceof File){
                writeFileFormData(outputStream,params.getKey(),(File)params.getValue());
            }else{
                writeStringFormData(outputStream,params.getKey(),(String)params.getValue());
            }
            outputStream.write("\r\n".getBytes());
        }
        outputStream.write(endBoundary.getBytes());
    }

    /**
     * 写入表单中的File item
     * @param outputStream
     * @param key
     * @param value
     */
    private void writeFileFormData(OutputStream outputStream,String key,File value)throws IOException{
        // --boundary
        // Content-Disposition: form-data; name="keyName"; fileName="abc.jpg"       //相当于key=value中的key
        // Content-Type: image/jpeg;
        //
        // file 的数据...
        StringBuilder builder = new StringBuilder();
        String mimeType = "application/octet-stream";
        if(MimeTypeMap.getSingleton().hasExtension(value.getName())){
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(value.getName());
        }
        builder.append(startBoundry).append("\r\n");
        builder.append("Content-Disposition: form-data; name ="+key).append("; fileName"+value.getName()).append("\r\n");
        builder.append("Content-Type:"+mimeType).append("\r\n");
        builder.append("\r\n");
        outputStream.write(builder.toString().getBytes(charset));

        if(outputStream instanceof CounterOutputStream){
            //如果是统计长度的，直接将长度写进流中即可
            ((CounterOutputStream) outputStream).write(value.length());
        }else{
            InputStream stream = new FileInputStream(value);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = stream.read(buffer)) != -1){
                outputStream.write(buffer,0,len);
            }
        }
    }

    /**
     * 写入表单中的String item
     * @param outputStream
     * @param key
     * @param value
     */
    private void writeStringFormData(OutputStream outputStream,String key,String value) throws IOException{
        // --boundary                                           //会在每个item前添加开始分隔符
        // Content-Disposition: form-data; name="keyName"       //相当于key=value中的key
        // Content-Type: text/plain; charset: "utf-8"
        //
        // String的数据...
        StringBuilder builder = new StringBuilder();
        builder.append(startBoundry).append("\r\n");
        builder.append("Content-Disposition: form-data; name ="+key).append("\r\n");
        builder.append("Content-Type: text/plain; charset:"+ charset).append("\r\n");
        builder.append("\r\n");
        builder.append(value);
        Logger.I("writeStringFormData :"+builder.toString());
        outputStream.write(builder.toString().getBytes(charset));

    }


    /**
     * 判断参数中是否有文件
     * @return
     */
    protected boolean hasFile(){
        for (Params params : paramList) {
            if(params.getValue() instanceof File){
                return true;
            }
        }

        return false;
    }

    protected String createBoundary(){
        return "AsyncMessageHanlder" + UUID.randomUUID();
    }

    /**
     * 设置请求的数据类型
     * @param contentType
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void formData(boolean isFormData){
        if (method.isOutputMethod()){
            this.isFormData = isFormData;
        }else{
            throw new IllegalArgumentException(method.getMethod() + " is not support outputstream");

        }
    }

    String buildParams() throws IOException{
        if(paramList.size() > 0 ){
            StringBuilder builder = new StringBuilder("&");
            // http://www.baidu.com?key=value&key1=value1
            for (Params params : paramList) {
                builder.append(URLEncoder.encode(params.getKey(),charset))
                        .append("=")
                        .append(URLEncoder.encode((String)params.getValue(),charset));
            }
            builder.deleteCharAt(0);
            Logger.I("buildeParams"+builder.toString());
            return builder.toString();
        }else{
            return "";
        }
    }

    @Override
    public String toString() {
        return "url = " + url + "; method = " + method + "; params = " + paramList.toString();
    }
}
