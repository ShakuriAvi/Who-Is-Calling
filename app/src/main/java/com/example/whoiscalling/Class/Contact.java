package com.example.whoiscalling.Class;

public class Contact {
    private String phoneNumber;
    private String nameOfMember;



    private String nickName;
    public Contact(){}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNameOfMember() {
        return nameOfMember;
    }

    public void setNameOfMember(String nameOfMember) {
        this.nameOfMember = nameOfMember;
    }

    public Contact(String phoneNumber, String nameOfMember, String nickName){
        this.nameOfMember = nameOfMember;
        this.phoneNumber = phoneNumber;
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
