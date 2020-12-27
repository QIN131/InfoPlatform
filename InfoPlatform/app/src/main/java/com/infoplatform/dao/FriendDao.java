package com.infoplatform.dao;

import com.infoplatform.data.Friend;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface FriendDao {
    //查询某个人的全部好友
    @Query("SELECT * FROM friend WHERE uid=:uid")
    List<Friend> getAllFriends(int uid);

    @Query("SELECT * FROM friend WHERE uid=:uid and fuid=:fid")
    Friend getFriend(int uid,int fid);

    //添加好友
    @Insert
    void insert(Friend friend);

    //更改状态
    @Update
    void update(Friend friend);

    //删除好友
    @Query("DELETE  FROM friend where uid=:uid and fuid=:fid")
    void deleteFriend(int uid,int fid);
}
