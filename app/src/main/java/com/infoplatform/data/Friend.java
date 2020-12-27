package com.infoplatform.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//好友
@Entity
public class Friend {
    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    private int id;
    private int uid;//自己ID
    private int fuid;//好友ID
    private String uname;//自己用户名
    private String fname;//好友用户名
    private int status;//状态 0：待处理，1：同意，2：拒绝

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getFuid() {
        return fuid;
    }

    public void setFuid(int fuid) {
        this.fuid = fuid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
