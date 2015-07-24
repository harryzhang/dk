package com.hehenian.liumi.exchange;
 
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
 
public class HttpsUtil {
 
	private static final String CHARSET="UTF-8";
	
    private static class TrustAnyTrustManager implements X509TrustManager {
 
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
 
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
 
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }
 
    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
 
    /**
     * post方式请求服务器(https协议)
     * 
     * @param url
     *            请求地址
     * @param content
     *            参数
     * @param charset
     *            编码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     */
    public static byte[] post(String url, String content)
            throws NoSuchAlgorithmException, KeyManagementException,
            IOException {
    	
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
                new java.security.SecureRandom());
 
        URL console = new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
        conn.setSSLSocketFactory(sc.getSocketFactory());
        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
        conn.setDoOutput(true);
        conn.connect();
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(content.getBytes(CHARSET));
        // 刷新、关闭
        out.flush();
        out.close();
        InputStream is = conn.getInputStream();
        if (is != null) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            is.close();
            return outStream.toByteArray();
        }
        return null;
    }
    
    
    
    public static String get(String path) throws Exception{
    	  HttpURLConnection httpConn=null;
    	  BufferedReader in=null;
    	  try {
    	   URL url=new URL(path);
    	   httpConn=(HttpURLConnection)url.openConnection();

    	   //读取响应
    	   if(httpConn.getResponseCode()==HttpURLConnection.HTTP_OK){
    	    StringBuffer content=new StringBuffer();
    	    String tempStr="";
    	    in=new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
    	    while((tempStr=in.readLine())!=null){
    	     content.append(tempStr);
    	    }
    	    return content.toString();
    	   }else{
    	    throw new Exception("请求出现了问题!");
    	   }
    	  } catch (IOException e) {
    	   e.printStackTrace();
    	  }finally{
    	   in.close();
    	   httpConn.disconnect();
    	  }
    	  return null;
    	 }

}
