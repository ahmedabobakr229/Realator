package com.example.document.realator;

/**
 * Created by document on 20/06/2018.
 */

public class Upload {
    private String mName;
    private String mImageUrl;

    public Upload(){

    }
    public Upload(String name , String ImageUrl){

        if (name.trim().equals("")){
            name = "no name";
        }

        mName = name;
        mImageUrl = ImageUrl;
    }
    public String getName (){
        return mName;
    }
    public void setName(String name){
        mName = name;
    }
    public String getImageUrl(){
        return mImageUrl;
    }
    public void setImageUrl(String ImageUrl){
        mImageUrl = ImageUrl;
    }
}
