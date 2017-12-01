package com.simon.bigfiledownload.download;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.simon.bigfiledownload.download.BigFileDownManager.TAG;

/**
 * auther: Simon zhang
 * Emaill:18292967668@163.com
 */
public class DownloadTask extends Thread{

    private int threadId;

    private String downloadUrl;

    private long startPos;

    private long endPos;

    private long mCurrentPos;

    private String targetPath;

    private  boolean isPause;

    private boolean isCancel;

    private IDownListener iDownListener;

    public DownloadTask(int threadId, String url, long startLocation, long endLocation, String targetPath, IDownListener iDownListener) {
        this.threadId=threadId;
        this.downloadUrl = url;
        this.startPos = startLocation;
        this.endPos = endLocation;
        this.targetPath = targetPath;
        this.iDownListener=iDownListener;
        this.mCurrentPos =startLocation;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }

    @Override
    public void run() {
        super.run();
        HttpURLConnection conn;
        InputStream in=null;
        RandomAccessFile raf=null;
        try {
            URL url=new URL(downloadUrl);
            conn=(HttpURLConnection)url.openConnection();
            //在头里面请求下载开始位置和结束位置
            conn.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);
            HttpUtils.setConnectionParams(conn);
            int responseCode=conn.getResponseCode();
            if(responseCode==206){
                in=conn.getInputStream();
                //创建可设置位置的文件
                raf = new RandomAccessFile(targetPath, "rwd");
                //设置每条线程写入文件的位置
                raf.seek(startPos);
                byte[] buffer = new byte[1024];
                int len;
                while ((len=in.read(buffer))!=-1){
                    if(isCancel){
                        Log.d(TAG,"url"+downloadUrl+"-task"+threadId+" is canceled...");
                        break;
                    }
                    if(isPause){
                        Log.d(TAG,"url"+downloadUrl+"-task"+threadId+" is paused...");
                        break;
                    }
                    raf.write(buffer,0,len);
                    mCurrentPos +=len;
                    Log.d(TAG,"threadId="+Thread.currentThread().getName()+"mCurrentPos="+mCurrentPos);
                    if(iDownListener!=null){
                        iDownListener.onProcess(mCurrentPos,threadId);
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            if(iDownListener!=null){
                iDownListener.onFailure(threadId);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if(iDownListener!=null){
                iDownListener.onFailure(threadId);
            }
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(raf!=null){
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
