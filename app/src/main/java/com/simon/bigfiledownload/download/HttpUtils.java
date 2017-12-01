package com.simon.bigfiledownload.download;

import java.net.HttpURLConnection;
import java.net.ProtocolException;

/**
 * auther: Simon zhang
 * Emaill:18292967668@163.com
 */

public class HttpUtils {

    public static  final int IO_BUFFER_SIZE=8*1024;
    private static final int CONNECTION_OUT_TIME=60*1000;
    private static final int READ_OUT_TIME=60*1000;

    public  static  void setConnectionParams(HttpURLConnection conn) throws ProtocolException {
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setReadTimeout(READ_OUT_TIME);
        conn.setConnectTimeout(CONNECTION_OUT_TIME);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
        conn.setRequestProperty(
                "Accept",
                "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
    }
}
