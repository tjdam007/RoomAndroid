package room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created By: Manoj Singh Rawal
 * Project : roomlibex
 * Copyright (c) 2018
 * on 5/2/18.
 */
@Entity(tableName = "user_table")
public class User implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    long userId;
    @NonNull
    String name;


    public User() {
    }

    @Ignore
    public User(String name) {
        this.name = name;
    }


    @NonNull
    public String getName() {
        return name;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeLong(userId);
    }

    @Ignore
    private User(Parcel parcel) {
        name = parcel.readString();
        userId = parcel.readLong();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[0];
        }
    };
}
