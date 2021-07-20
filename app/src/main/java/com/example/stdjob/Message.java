package com.example.stdjob;

import androidx.annotation.NonNull;

public class Message {
    String message;
    Boolean isMine;


    public Message(String message, Boolean isMine) {
        this.message = message;
        this.isMine = isMine;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getMine() {
        return isMine;
    }

    public void setMine(Boolean mine) {
        isMine = mine;
    }


    @NonNull
    @Override
    public String toString() {
        return message;
    }
}
