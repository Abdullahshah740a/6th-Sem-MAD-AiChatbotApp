package com.example.chatbot;

public class MessageModel {
    public  static  String sent_by_me="Me";
    public  static  String sent_by_bot="Bot";

    String Message;
    String sentBy;
    public  MessageModel(String Message,String sentBy){
        this.Message=Message;
        this.sentBy=sentBy;
    }

    String getMessage(){
        return  Message;
    }

    void setMessage(String Message){
        this.Message=Message;
    }

    String getSentBy(){
        return  sentBy;
    }

    void setSentBy(String sentBy){
        this.sentBy=sentBy;
    }

}
