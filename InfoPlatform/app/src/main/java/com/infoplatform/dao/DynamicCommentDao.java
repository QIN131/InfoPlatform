package com.infoplatform.dao;

import com.infoplatform.data.DynamicComment;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DynamicCommentDao {
    //添加评论
    @Insert
    void insert(DynamicComment dynamicComment);

    //获取某个动态的全部评论
    @Query("SELECT * from dynamiccomment where dynamicid=:dynamicid")
    List<DynamicComment> getList(int dynamicid);
}
