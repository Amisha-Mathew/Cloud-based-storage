package com.example.demo;

import java.io.File;

public class UserFiles {
    
    private Integer userId;
    private int numFiles;
    private File filename;

    public UserFiles(Integer userId, int numFiles, File filename)
    {
        this.userId = userId;
        this.numFiles = numFiles;
        this.filename = filename;
    }

    public Integer getUserID()
    {
        return userId;
    }

    // public void setUserID(Integer userId)
    // {
    //     this.userId = userId;
    // }

    public Integer getNumFiles()
    {
        return numFiles;
    }

    public void setNumFiles(int numFiles)
    {
        this.numFiles = numFiles;
    }

    public File getFile()
    {
        return filename;
    }

    public void setFile(File filename)
    {
        this.filename = filename;
    }
}
