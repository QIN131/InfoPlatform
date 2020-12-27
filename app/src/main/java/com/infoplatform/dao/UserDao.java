package com.infoplatform.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import com.infoplatform.data.User;
import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    //根据ID获取某个人信息
    @Query("SELECT * FROM user WHERE id=:id")
    User getUserById(int id);

    //根据账号获取某个人信息
    @Query("SELECT * FROM user WHERE name=:name")
    User getUser(String name);

    //根据账号模糊检索用户
    @Query("SELECT * FROM user WHERE name LIKE '%' || :search || '%'")
    List<User> searchUsers(String search);

    //添加用户
    @Insert
    void insert(User user);

    //更改个人信息
    @Update
    void update(User... users);

//    @Delete
//    void delete(int id);
}
