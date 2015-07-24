package com.shove.util;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

public class HttpsUtils {
	private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public void httpss(String url, String from) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
        //URL console = new URL("https://www.google.com/accounts/LoginAuth?continue=http%3A%2F%2Fwww.google.cn%2F&hl=zh-CN");
        URL console = new URL(url);
        String postData=from;
        HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
        conn.setSSLSocketFactory(sc.getSocketFactory());
        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());

        conn.setDoInput(true);
        conn.setDoOutput(true);
        BufferedOutputStream hurlBufOus=new BufferedOutputStream(conn.getOutputStream());
        hurlBufOus.write(postData.getBytes());//这里面已经将RequestMethod设置为POST.前面设置无效
        hurlBufOus.flush();

        conn.connect();
        InputStream ins=conn.getInputStream();
        BufferedOutputStream fileBufOus=new BufferedOutputStream(new FileOutputStream("c:/testWeb.html"));
        int read=0;
        byte[] byteBuf=new byte[9412];
        while((read=ins.read(byteBuf))!=-1){
            fileBufOus.write(byteBuf, 0, read);
        }

        if(ins!=null)ins.close();
        if(fileBufOus!=null)fileBufOus.close();
    }
}
