package com.anvisys.nestinguard.Common;

import java.security.PrivateKey;

public class Employee {
     private String id;
     private String name;
     private  String mobile_no;
     private  String Add;
     private String  StartTime;
     private Boolean ChekStatus;

    public void setChekStatus(Boolean chekStatus) {
        ChekStatus = chekStatus;
    }

    public Employee(String id, String name, String mobile_no, String add, String startTime, Boolean chekStatus, int image) {
        this.id = id;
        this.name = name;
        this.mobile_no = mobile_no;
        Add = add;
        StartTime = startTime;
        ChekStatus = chekStatus;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public Boolean getChekStatus() {
        return ChekStatus;
    }

    public String getName() {
        return name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public String getAdd() {
        return Add;
    }

    public String getStartTime() {
        return StartTime;
    }

    public int getImage() {
        return image;
    }

    private int image;
//constucter


 }
