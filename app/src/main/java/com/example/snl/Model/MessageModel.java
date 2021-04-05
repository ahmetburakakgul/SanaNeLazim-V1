package com.example.snl.Model;

public class MessageModel {

    private String mesaj,from,to;

    public MessageModel(String mesaj, String from, String to) {
        this.mesaj = mesaj;
        this.from = from;
        this.to = to;
    }

    public MessageModel() {
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
