package com.example.demo.service;

import java.io.File;

public class FileService {

    // reconfigure the path of log file as needed
    protected static String logString = "C:\\Users\\PC\\Downloads\\testlogs\\apilog.log";

    public boolean fileSetup(){
        File f;
            try{
                f = new File(logString);
                if(!f.exists()){ // if file does not exists, create a new file;
                    System.out.println("Log File Created - Setup Success");
                    f.createNewFile();
                    return true;
                }
                System.out.println("Log File Exists - Setup Success");
                return true;
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
    };


}
