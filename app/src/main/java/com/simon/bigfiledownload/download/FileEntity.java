package com.simon.bigfiledownload.download;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * auther: Simon zhang
 * Emaill:18292967668@163.com
 */

public class FileEntity extends DataSupport {
    @Column(unique = true, defaultValue ="0")
    private int _id;

    private String url;

    private String targetFolder;

    private String targetPath;

    private long  fileSize;

    @Column(defaultValue ="0")
    private int thread_1_pos;

    @Column(defaultValue ="0")
    private int thread_2_pos;

    private int thread_3_pos;

    private int status;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTargetFolder() {
        return targetFolder;
    }

    public void setTargetFolder(String targetFolder) {
        this.targetFolder = targetFolder;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public int getThread_1_pos() {
        return thread_1_pos;
    }

    public void setThread_1_pos(int thread_1_pos) {
        this.thread_1_pos = thread_1_pos;
    }

    public int getThread_2_pos() {
        return thread_2_pos;
    }

    public void setThread_2_pos(int thread_2_pos) {
        this.thread_2_pos = thread_2_pos;
    }

    public int getThread_3_pos() {
        return thread_3_pos;
    }

    public void setThread_3_pos(int thread_3_pos) {
        this.thread_3_pos = thread_3_pos;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
