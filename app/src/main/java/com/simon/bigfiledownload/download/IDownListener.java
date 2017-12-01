package com.simon.bigfiledownload.download;

/**
 * auther: Simon zhang
 * Emaill:18292967668@163.com
 */

public interface IDownListener {

    /**
     * 单个线程文件下载进度回调
     * @param downLoadPos   线程下载到的进度位置
     * @param threadId           线程id
     */
    void onProcess(long downLoadPos,int threadId);

    /**
     * 下载失败
     * @param threadId           线程id
     */
    void onFailure(int threadId);

    /**
     * 下载成功
     * @param threadId           线程id
     */
    void onComplete(int threadId);

}
