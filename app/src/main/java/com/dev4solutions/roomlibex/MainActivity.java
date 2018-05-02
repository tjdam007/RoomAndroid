package com.dev4solutions.roomlibex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

import java.util.List;

import room.Callback;
import room.entity.User;
import room.repo.UserRepo;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchData();
    }

    private void fetchData() {
        new UserRepo(getApplication())
                .getAllUser(new Callback<List<User>>() {
                    @Override
                    public void onCallback(List<User> users) {
                        recyclerView.setAdapter(new Adapter(MainActivity.this, users));
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            startActivity(new Intent(this, AddEditActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private class Adapter extends RecyclerView.Adapter<VH> {

        private final List<User> dataList;

        public Adapter(Context context, List<User> users) {
            this.dataList = users;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            final User user = dataList.get(position);
            holder.name.setText(String.format("ID: %d  Name: %s", user.getUserId(), user.getName()));
            holder.tvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                    intent.putExtra(Keys.DATA, user);
                    startActivity(intent);
                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new UserRepo(getApplication())
                            .delete(user, new Callback<Integer>() {
                                @Override
                                public void onCallback(Integer integer) {
                                    fetchData();
                                }
                            });
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }

    private class VH extends RecyclerView.ViewHolder {
        TextView name, tvEdit, delete;

        public VH(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView);
            tvEdit = itemView.findViewById(R.id.tvEdit);
            delete = itemView.findViewById(R.id.tvdelete);
        }
    }
}
