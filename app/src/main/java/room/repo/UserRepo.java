package room.repo;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import room.Callback;
import room.Database;
import room.dao.UserDao;
import room.entity.User;

/**
 * Created By: Manoj Singh Rawal
 * Project : roomlibex
 * Copyright (c) 2018
 * on 5/2/18.
 */
public class UserRepo {

    UserDao userDao;

    public UserRepo(Application application) {
        Database database = Database.getInstance(application);
        userDao = database.getUserDoa();
    }

    public void insert(User user) {
        new InsertTask(userDao)
                .execute(user);
    }

    public void getAllUser(Callback<List<User>> callback) {
        new QueryTask(userDao, callback)
                .execute();
    }

    public void update(User user, Callback<Integer> callback) {
        new UpdateTask(userDao, callback)
                .execute(user);
    }

    public void delete(User user, Callback<Integer> callback) {
        new DeleteTask(userDao, callback)
                .execute(user);
    }

    private static class InsertTask extends AsyncTask<User, Void, Long> {

        private static final String TAG = "InsertTask";
        private final UserDao userDao;

        InsertTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Long doInBackground(User... users) {
            return userDao.insertUser(users[0]);
        }

        @Override
        protected void onPostExecute(Long val) {
            super.onPostExecute(val);
            Log.d(TAG, "Inserted Id: " + val);
        }
    }

    private static class QueryTask extends AsyncTask<Void, Void, List<User>> {

        private static final String TAG = "QueryTask";
        private final UserDao userDao;
        private Callback<List<User>> listCallback;

        public QueryTask(UserDao userDao, Callback<List<User>> callback) {
            this.userDao = userDao;
            listCallback = callback;
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            return userDao.getAllUser();
        }

        @Override
        protected void onPostExecute(List<User> users) {
            super.onPostExecute(users);
            listCallback.onCallback(users);
        }
    }

    private static class UpdateTask extends AsyncTask<User, Void, Integer> {

        private static final String TAG = "UpdateTask";
        private final UserDao userDao;
        private Callback<Integer> callback;

        public UpdateTask(UserDao userDao, Callback<Integer> callback) {
            this.userDao = userDao;
            this.callback = callback;
        }

        @Override
        protected Integer doInBackground(User... users) {
            return userDao.update(users[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            callback.onCallback(integer);
        }
    }

    private static class DeleteTask extends AsyncTask<User, Void, Integer> {

        private static final String TAG = "DeleteTask";
        private final UserDao userDao;
        private Callback<Integer> callback;

        public DeleteTask(UserDao userDao, Callback<Integer> callback) {
            this.userDao = userDao;
            this.callback = callback;
        }

        @Override
        protected Integer doInBackground(User... users) {
            return userDao.delete(users[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            callback.onCallback(integer);
        }
    }
}
