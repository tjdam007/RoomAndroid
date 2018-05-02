package room;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import room.dao.UserDao;
import room.entity.User;

/**
 * Created By: Manoj Singh Rawal
 * Project : roomlibex
 * Copyright (c) 2018
 * on 5/2/18.
 */
@android.arch.persistence.room.Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    private static Database instance;

    public abstract UserDao getUserDoa();

    public static Database getInstance(Application application) {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(application, Database.class, "roomlib.db")
                            .build();
                }
            }
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
