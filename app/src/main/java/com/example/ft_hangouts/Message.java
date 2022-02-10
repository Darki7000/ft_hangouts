package com.example.ft_hangouts;

public class Message {
    private int _id;
    private int _contactId;
    private String _message;
    private boolean _isFromMe;

    public Message() {}
    public Message(int id, int contactId, String message, boolean isFromMe) {
        _id = id;
        _contactId = contactId;
        _message = message;
        _isFromMe = isFromMe;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setContactId(int contactId) {
        _contactId = contactId;
    }

    public void setMessage(String message) {
        _message = message;
    }

    public void setIsFromMe(boolean isFromMe) {
        _isFromMe = isFromMe;
    }

    public int getId() {
        return _id;
    }

    public int getContactId() {
        return _contactId;
    }

    public String getMessage() {
        return _message;
    }

    public boolean getIsFromMe() {
        return _isFromMe;
    }
}
