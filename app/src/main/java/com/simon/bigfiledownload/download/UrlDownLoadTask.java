package com.simon.bigfiledownload.download;

import android.net.Uri;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * auther: Simon zhang
 * Emaill:18292967668@163.com
 *
 * 大文件的下载任务,每个下载任务固定搭配3个线程
 */
public class UrlDownLoadTask  implements IDownListener{

    private  FileEntity fileEntity;

    private IUrlDownListener urlDownListener;
    /**
     * 单个下载任务对应的线程数
     */
    private static int taskThreadSize = 3;

    private DownloadTask[] downloadTasks=new DownloadTask[taskThreadSize];

    public UrlDownLoadTask(IUrlDownListener urlDownListener,final String downUrl) {
        this.urlDownListener=urlDownListener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url;
                    HttpURLConnection conn;
                    url = new URL(downUrl);
                    conn=(HttpURLConnection)url.openConnection();
                    HttpUtils.setConnectionParams(conn);
                    conn.connect();
                    int responseCode = conn.getResponseCode();
                    if(responseCode==200){
                       int contentLength = conn.getContentLength();
                        if(contentLength<0){
                            //异常处理
                        }
                        fileEntity=new FileEntity();
                        fileEntity.setUrl(downUrl);
                        fileEntity.setFileSize(contentLength);
                        fileEntity.setStatus(BigFileDownManager.NONE);
                        fileEntity.setTargetFolder(BigFileDownManager.getInstance().getTargetFolder());
                        fileEntity.setTargetPath(BigFileDownManager.getInstance().getTargetFolder()+ File.separator+ Uri.parse(downUrl).getLastPathSegment());


                        //构建文件
                        File file = new File(fileEntity.getTargetFolder());
                        if(!file.exists()){
                            file.mkdir();
                        }
                        RandomAccessFile raf=new RandomAccessFile(fileEntity.getTargetPath(),"rw");
                        raf.setLength(contentLength);
                        raf.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                long blockSize=fileEntity.getFileSize()%taskThreadSize==0?fileEntity.getFileSize()/taskThreadSize:fileEntity.getFileSize()/taskThreadSize+1;
                for (int i = 0; i <taskThreadSize; i++) {
                    long startPos=i*blockSize;
                    long endPos=startPos+blockSize>fileEntity.getFileSize()?fileEntity.getFileSize():startPos+blockSize-1;
                    downloadTasks[i]=new DownloadTask(i,fileEntity.getUrl(),startPos,endPos,fileEntity.getTargetPath(),UrlDownLoadTask.this);
                    downloadTasks[i].setName("Thread"+i);
                }

                startDownload();
            }
        }).start();
    }

    public UrlDownLoadTask(IUrlDownListener urlDownListener,final FileEntity entity) {
        this.urlDownListener=urlDownListener;
        this.fileEntity=entity;
        long fileSize = fileEntity.getFileSize();
        long blockSize=fileSize%taskThreadSize==0?fileSize/taskThreadSize:fileSize/taskThreadSize+1;
        for (int i = 0; i <taskThreadSize; i++) {
            long startPos;
            if(i==0){
                startPos=entity.getThread_1_pos();
            }else if(i==1){
                startPos=entity.getThread_2_pos();
            }else{
                startPos=entity.getThread_3_pos();
            }
            long endPos=i*blockSize+blockSize>fileSize?fileSize:i*blockSize+blockSize-1;
            downloadTasks[i]=new DownloadTask(i,entity.getUrl(),startPos,endPos,entity.getTargetPath(),UrlDownLoadTask.this);
        }
    }


    public  void startDownload() {
        for (int i = 0; i <taskThreadSize; i++){
             downloadTasks[i].start();
        }
    }

    public  void pause() {
    }

    public void resume() {
    }

    public void cancel() {
    }

    public  void setFileEntity(FileEntity fileEntity) {
        this.fileEntity = fileEntity;
    }

    public String getUrl() {
        return fileEntity.getUrl();
    }

    public int getmCurrentStatus() {
        return fileEntity.getStatus();
    }


    @Override
    public void onProcess(long downLoadPosition, int threadId) {
    }

    @Override
    public void onFailure(int threadId) {
    }

    @Override
    public void onComplete(int threadId) {
    }


}
