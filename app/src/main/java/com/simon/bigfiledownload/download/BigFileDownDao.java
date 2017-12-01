package com.simon.bigfiledownload.download;

import android.support.annotation.NonNull;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * auther: Simon zhang
 * Emaill:18292967668@163.com
 *
 * 数据库抽象层，为之后能够灵活替换做的一次抽象
 */
public class BigFileDownDao {

    public List<FileEntity> queryAll(){
        List<FileEntity> fileEntities = DataSupport.findAll(FileEntity.class);
        return fileEntities;
    }

    public  boolean isUrlExist(@NonNull String url){
        List<FileEntity> fileEntities = DataSupport.where("url =", url).find(FileEntity.class);
        if(fileEntities!=null&&fileEntities.size()>0){
            return true;
        }
        return false;
    }


}
