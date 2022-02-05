package com.example.ft_hangouts;

public class Contact {
    private String _name;
    private String _phoneNum;
    private String _email;
    private String _address;
    private String _zipCode;

    public Contact() {}
    public Contact(String name, String phoneNum, String email, String address, String zipCode) {
        _name = name;
        _phoneNum = phoneNum;
        _email = email;
        _address = address;
        _zipCode = zipCode;
    }

    public String getContactName() {
        return this._name;
    }
    public String getContactPhone() {
        return this._phoneNum;
    }
    public String getContactEmail() {
        return this._email;
    }
    public String getContactAddress() {
        return this._address;
    }
    public String getContactZip() {
        return this._zipCode;
    }

    public void setContactName(String name) {
        this._name = name;
    }
    public void setContactPhone(String phoneNum) {
        this._phoneNum = phoneNum;
    }
    public void setContactEmail(String email) {
        this._email = email;
    }
    public void setContactAddress(String address) {
        this._address = address;
    }
    public void setContactZip(String zipCode) {
        this._zipCode = zipCode;
    }
}

