package com.syscom.apps.myapps.domains;

import java.io.Serializable;

/**
 * Created by Eric LEGBA on 29/08/17.
 */

public class Message implements Serializable {

    private String subject;
    private String content;


    public Message(){

    }

    public  Message(String subject, String content){
        this.subject = subject;
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
