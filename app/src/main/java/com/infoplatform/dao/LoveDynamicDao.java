package com.infoplatform.dao;

import com.infoplatform.data.LoveDynamic;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface LoveDynamicDao {
    @Insert
    void insert(LoveDynamic loveDynamic);

    @Query("DELETE  FROM lovedynamic WHERE id=:id")
    void deleteByID(int id);

    @Query("SELECT * FROM lovedynamic WHERE uid=:uid and dynamicid=:did")
    LoveDynamic getData(int uid,int did);
}
