package com.simon.bigfiledownload.download;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * auther: Simon zhang
 * Emaill:18292967668@163.com
 */
public class BigFileDownManager implements IUrlDownListener{

    public static String TAG="BIG_DOWN";
    /**
     *存活的时间
     */
    private static final int KEEP_ALIVE_TIME = 0;
    /**
     *  时间单位
     */
    private static final TimeUnit UNIT = TimeUnit.HOURS;

/*   *//**
     * 线程池执行器
     *//*
    public static ThreadPoolExecutor executor;
  */
    public static ExecutorService executor;
    /**
     * 核心线程池的数量，同时能执行的线程数量，默认6个,可同时下载两个大文件
     */
    private static int corePoolSize = 6;

    /**
     * 使用单例模式
     */
    private static BigFileDownManager mInstance;

    /**
     * 目标目录的地址
     */
    private static String targetFolder;

    /**
     * 数据操作类
     */
    private  BigFileDownDao dao;

    /**
     * 当前正在运行的任务， String 对应的是urlTask,
     */
    private List<UrlDownLoadTask> currentTasks ;

    //定义下载状态常量
    public static final int NONE = 0;         //无状态  --> 等待
    public static final int WAITING = 1;      //等待    --> 下载，暂停
    public static final int DOWNLOADING = 2;  //下载中  --> 暂停，完成，错误
    public static final int PAUSE = 3;        //暂停    --> 等待，下载
    public static final int FINISH = 4;       //完成    --> 重新下载
    public static final int ERROR = 5;        //错误    --> 等待

    public static BigFileDownManager getInstance() {
        if (null == mInstance) {
            synchronized (BigFileDownManager.class) {
                if (null == mInstance) {
                    mInstance = new BigFileDownManager();
                }
            }
        }
        return mInstance;
    }

    public BigFileDownManager() {
        executor= Executors.newFixedThreadPool(corePoolSize);
        dao =new BigFileDownDao();
        currentTasks= getTaskFromDataBase();
    }

    public void addTask(@NonNull String url){
       if(TextUtils.isEmpty(url)){return;}

        UrlDownLoadTask task = getTaskByUrl(url);
        if(task==null){
            task=new UrlDownLoadTask(this,url);
        }

        //无状态或者下载错误才能重新开始
        if(task.getmCurrentStatus()==NONE||task.getmCurrentStatus()==ERROR){
            task.startDownload();
        }
    }


    public void start(@NonNull String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (getTaskByUrl(url)!=null) {
            UrlDownLoadTask urlDownLoadTask = getTaskByUrl(url);
            urlDownLoadTask.startDownload();
        }
    }

    public void pause(@NonNull String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (getTaskByUrl(url)!=null) {
            UrlDownLoadTask urlDownLoadTask = getTaskByUrl(url);
            urlDownLoadTask.pause();
        }
    }

    public void resume(@NonNull String url){
        if (TextUtils.isEmpty(url)) {
            return ;
        }
        if (getTaskByUrl(url)!=null) {
            UrlDownLoadTask urlDownLoadTask = getTaskByUrl(url);
            urlDownLoadTask.resume();
        }
    }

    public void cancel(@NonNull String url){
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (getTaskByUrl(url)!=null) {
            UrlDownLoadTask urlDownLoadTask = getTaskByUrl(url);
            urlDownLoadTask.cancel();
        }
    }


    public UrlDownLoadTask getTaskByUrl(@NonNull String url) {
        for (UrlDownLoadTask task : currentTasks) {
            if (url.equals(task.getUrl())) {
                return task;
            }
        }
        return null;
    }

    @Override
    public void onProcess(int downLoadPosition, String url) {
    }

    @Override
    public void failure(String url) {
    }

    @Override
    public void complete(String url) {
    }

    public static ExecutorService getExecutor() {
        return executor;
    }

    public void setTargetFolder(String targetFolder) {
        this.targetFolder = targetFolder;
    }

    public  String getTargetFolder() {
        return targetFolder;
    }


    public List<UrlDownLoadTask> getTaskFromDataBase() {
        List<FileEntity> fileEntities = dao.queryAll();
        List<UrlDownLoadTask> tasks=new ArrayList<>();
        for(FileEntity entity:fileEntities){
            UrlDownLoadTask task=new UrlDownLoadTask(this,entity);
            tasks.add(task);
        }
        return tasks;
    }
}
