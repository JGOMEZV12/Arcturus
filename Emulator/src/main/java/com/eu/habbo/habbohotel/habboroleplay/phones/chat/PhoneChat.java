package com.eu.habbo.habbohotel.habboroleplay.phones.chat;

import java.util.Date;

public class PhoneChat {
    private int id;
    private int type; // 1 = msg, 2 = WhatsApp
    private int senderId;
    private String senderName;
    private int receiverId;
    private String receiverName;
    private String message;
    private Date timestamp;

    public PhoneChat(int id, int type, int senderId, String senderName, int receiverId, String receiverName, String message, Date timestamp) {
        this.id = id;
        this.type = type;
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public int getSenderId() {
        return senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
