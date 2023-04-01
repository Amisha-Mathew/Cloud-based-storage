package com.example.demo;


public class Model {
    
    private Integer userId;
    private int numFiles;
    private byte[] filename;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
  

   

    

    public Model(int id, int userId2, int numFiles2, String filename2, byte[] fileContent) {
        this.userId = userId2;
        this.numFiles = numFiles2;
        this.filename = fileContent;
        this.userName = filename2;
    }

    public void setUserID(Integer userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
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

    public byte[] getFile()
    {
        return filename;
    }

    public void setFile(byte[] filename)
    {
        this.filename = filename;
    }
}

