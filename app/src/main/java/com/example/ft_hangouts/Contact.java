package com.example.ft_hangouts;

public class Contact {
    private int _id;
    private String _name;
    private String _phoneNum;
    private String _email;
    private String _address;
    private String _zipCode;

    public Contact() {}
    public Contact(int id, String name, String phoneNum, String email, String address, String zipCode) {
        _id = id;
        _name = name;
        _phoneNum = phoneNum;
        _email = email;
        _address = address;
        _zipCode = zipCode;
    }

    public int getId() {
        return this._id;
    }
    public String getName() {
        return this._name;
    }
    public String getPhone() {
        return this._phoneNum;
    }
    public String getEmail() {
        return this._email;
    }
    public String getAddress() {
        return this._address;
    }
    public String getZip() {
        return this._zipCode;
    }

    public void setId(int id) {
        this._id = id;
    }
    public void setName(String name) {
        this._name = name;
    }
    public void setPhone(String phoneNum) {
        this._phoneNum = phoneNum;
    }
    public void setEmail(String email) {
        this._email = email;
    }
    public void setAddress(String address) {
        this._address = address;
    }
    public void setZip(String zipCode) {
        this._zipCode = zipCode;
    }
}

