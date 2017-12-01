package com.simon.bigfiledownloadandroid.test;

import java.io.File;
import java.io.RandomAccessFile;

public class Test {

    public static void main(String[] args) {
        String filePath="E:\\android developer\\BigFileDownLoad\\Test\\src\\main\\java\\com\\simon\\bigfiledownloadandroid\\test\\center.html";

        String copyPath="E:\\android developer\\BigFileDownLoad\\Test\\src\\main\\java\\com\\simon\\bigfiledownloadandroid\\test\\copy.html";
        RandomAccessFile rafIn=null;
        RandomAccessFile rafOut=null;

        String path="E:\\c\\d";
        try {
            File file=new File(path);

            if(!file.exists()){
                boolean mkdir = file.mkdirs();
                System.out.println("mkdir="+mkdir);
            }
            if(file.exists()){
                System.out.println("exists="+file.exists());
            }
            boolean isDirectory=file.isDirectory();
            if(isDirectory){
                System.out.println("isDirectory="+isDirectory);
            }

      /*    rafIn=new RandomAccessFile(filePath,"r");
            rafOut=new RandomAccessFile(copyPath,"rws");
            byte[] buffer=new byte[1024];
            int len=0;

            rafIn.seek(10);
            rafOut.seek(10);
            while ((len=rafIn.read(buffer))!=-1){
                rafOut.write(buffer,0,len);
                System.out.println(new String(buffer,0,len));
            }

            rafIn.seek(0);
            rafOut.seek(0);
            while ((len=rafIn.read(buffer))!=-1){
                rafOut.write(buffer,0,len);
                System.out.println(new String(buffer,0,len));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
