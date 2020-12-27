package com.infoplatform.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//动态信息评论
@Entity
public class DynamicComment {
    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    private int id;
    private int dynamicid;
    private int uid;
    private String comment;
    private String sendtime;
    private String uname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDynamicid() {
        return dynamicid;
    }

    public void setDynamicid(int dynamicid) {
        this.dynamicid = dynamicid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
