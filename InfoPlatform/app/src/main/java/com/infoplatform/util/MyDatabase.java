package com.infoplatform.util;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.infoplatform.dao.DynamicCommentDao;
import com.infoplatform.dao.DynamicDao;
import com.infoplatform.dao.FriendDao;
import com.infoplatform.dao.LoveDynamicDao;
import com.infoplatform.dao.NoticeDao;
import com.infoplatform.data.Dynamic;
import com.infoplatform.data.DynamicComment;
import com.infoplatform.data.Friend;
import com.infoplatform.data.LoveDynamic;
import com.infoplatform.data.Notice;
import com.infoplatform.data.User;
import com.infoplatform.data.Admin;
import com.infoplatform.dao.UserDao;
import com.infoplatform.dao.AdminDao;

@Database(entities = {User.class,Admin.class, Dynamic.class, LoveDynamic.class, DynamicComment.class, Friend.class, Notice.class},
        version = 1,exportSchema = false)
public abstract class MyDatabase extends RoomDatabase{
    private static final String DB_NAME = "MyDatabase.db";
    private static volatile MyDatabase instance;

    public static synchronized MyDatabase getInstance(Context context){
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static MyDatabase create(final Context context) {
        return Room.databaseBuilder( context,MyDatabase.class,DB_NAME).allowMainThreadQueries().build();
    }

    public abstract UserDao getUserDao();
    public abstract AdminDao getAdminDao();
    public abstract DynamicDao getDynamicDao();
    public abstract LoveDynamicDao getLoveDynamicDao();
    public abstract DynamicCommentDao getDynamicCommentDao();
    public abstract FriendDao getFriendDao();
    public abstract NoticeDao getNoticeDao();
}
