package com.infoplatform.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.infoplatform.data.Admin;

import java.util.List;

@Dao
public interface AdminDao {
    //获取所有管理员
    @Query("SELECT * FROM admin")
    List<Admin> getAdmins();

    //根据ID获取管理员信息
    @Query("SELECT * FROM admin WHERE id=:id")
    Admin getAdmin(int id);

    //根据账号获取管理员信息
    @Query("SELECT * FROM admin WHERE name=:name")
    Admin getAdminByName(String name);

    //添加管理员
    @Insert
    void insert(Admin admin);
}
