package com.infoplatform.dao;

import com.infoplatform.data.Notice;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NoticeDao {
    //获取某人的提醒
    @Query("SELECT * FROM notice where uid=:uid")
    List<Notice> getNotices(int uid);

    //添加提醒
    @Insert
    void insert(Notice notice);

    //删除提醒
    @Query("DELETE  FROM notice where id=:id")
    void deleteNotice(int id);
}
