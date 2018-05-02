package room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import room.entity.User;

/**
 * Created By: Manoj Singh Rawal
 * Project : roomlibex
 * Copyright (c) 2018
 * on 5/2/18.
 */
@Dao
public interface UserDao {
    @Insert
    long insertUser(User user);

    @Query("Select * from user_table")
    List<User> getAllUser();

    @Update
    int update(User user);

    @Delete
    int delete(User user);
}
