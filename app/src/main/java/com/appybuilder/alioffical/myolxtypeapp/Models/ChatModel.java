package com.appybuilder.alioffical.myolxtypeapp.Models;

public class ChatModel {
    public String strSender,strReciver;
    public String strPhoneNo;

    public ChatModel(String strSender, String strReciver, String strPhoneNo) {
        this.strSender = strSender;
        this.strReciver = strReciver;
        this.strPhoneNo = strPhoneNo;
    }

    public ChatModel() {

    }

    public String getStrSender() {
        return strSender;
    }

    public void setStrSender(String strSender) {
        this.strSender = strSender;
    }

    public String getStrReciver() {
        return strReciver;
    }

    public void setStrReciver(String strReciver) {
        this.strReciver = strReciver;
    }

    public String getStrPhoneNo() {
        return strPhoneNo;
    }

    public void setStrPhoneNo(String strPhoneNo) {
        this.strPhoneNo = strPhoneNo;
    }
}
