package com.infoplatform.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//点赞动态信息
@Entity
public class LoveDynamic {
    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    private int id;
    private int dynamicid;
    private int uid;

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
}
