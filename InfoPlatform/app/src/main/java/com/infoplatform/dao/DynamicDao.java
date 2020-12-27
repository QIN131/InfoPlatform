package com.infoplatform.dao;

import com.infoplatform.data.Dynamic;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DynamicDao {
    //获取某个用户的全部动态
    @Query("SELECT * FROM dynamic WHERE uid=:uid")
    List<Dynamic> getMyDynamics(int uid);

    //获取其他人的全部动态
    @Query("SELECT * FROM dynamic WHERE uid!=:uid and ischeck=1")
    List<Dynamic> getOtherDynamics(int uid);

    //获取没有审核过的全部动态
    @Query("SELECT * FROM dynamic WHERE ischeck=0")
    List<Dynamic> getNoCheckDynamics();

    //获取最后一个动态
    @Query("SELECT * FROM dynamic order by id desc limit 1")
    Dynamic getLast();

    //根据ID获取某个动态信息
    @Query("SELECT * FROM dynamic WHERE id=:id")
    Dynamic getById(int id);

    //搜素动态
    @Query("SELECT * FROM dynamic WHERE title LIKE '%' || :search || '%' or content LIKE '%' || :search || '%'")
    List<Dynamic> searchdynamics(String search);

    //添加动态
    @Insert
    void insert(Dynamic dynamic);

    //更改动态
    @Update
    void update(Dynamic dynamic);

    //删除某个动态
    @Query("DELETE  FROM dynamic where id=:id")
    void deleteByID(int id);
}
