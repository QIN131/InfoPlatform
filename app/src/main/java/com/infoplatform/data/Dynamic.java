package com.infoplatform.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//动态信息
@Entity
public class Dynamic {
    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    private int id;
    private String title; //标题
    private String content;//内容
    private String imgs;//图片,多个用逗号分隔
    private int uid;//发表者ID
    private String sendtime;//发布时间
    private int ischeck;//是否审核,0:未审核，1：审核通过，2：审核不通过

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public int getIscheck() {
        return ischeck;
    }

    public void setIscheck(int ischeck) {
        this.ischeck = ischeck;
    }
}
