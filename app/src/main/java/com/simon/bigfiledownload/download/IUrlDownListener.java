package com.simon.bigfiledownload.download;

/**
 * auther: Simon zhang
 * Emaill:18292967668@163.com
 */

public interface IUrlDownListener {

    /**
     * 单个文件下载进度回调
     * @param downLoadPosition   线程下载到的进度位置
     * @param url           url
     */
    void onProcess(int downLoadPosition,String url);

    /**
     * 下载失败
     * @param url
     */
    void failure(String url);

    /**
     * 下载成功
     * @param url
     */
    void complete(String url);

}
