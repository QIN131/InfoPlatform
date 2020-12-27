package com.infoplatform.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//管理员
@Entity
public class Admin {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;// 昵称
    private String password;// 密码

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
