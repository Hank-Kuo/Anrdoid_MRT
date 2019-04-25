package com.example.guozhenyuan.myapplication;

import java.util.Date;

public class Chatmessage {
    private String messageText;
    private  String messageUser;
    private String messageTime;


    public Chatmessage(String messageText, String messageUser,String messageTime) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.messageTime=messageTime;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }
    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }


}
