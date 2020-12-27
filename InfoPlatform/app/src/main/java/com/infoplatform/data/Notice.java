package com.infoplatform.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//提醒
@Entity
public class Notice {
    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    private int id;

    private int uid;
    private String words;

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

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}
