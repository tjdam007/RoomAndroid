package com.dev4solutions.roomlibex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import room.Callback;
import room.entity.User;
import room.repo.UserRepo;

public class AddEditActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        editText = findViewById(R.id.name);
        Button button = findViewById(R.id.addButton);

        if (getIntent().hasExtra(Keys.DATA)) {
            setTitle(R.string.edit);
            final User user = getIntent().getParcelableExtra(Keys.DATA);
            editText.setText(user.getName());
            editText.setSelection(user.getName().length());
            button.setText(R.string.edit);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String value = editText.getText().toString();
                    user.setName(value);

                    if (!TextUtils.isEmpty(value)) {
                        new UserRepo(getApplication())
                                .update(user, new Callback<Integer>() {
                                    @Override
                                    public void onCallback(Integer integer) {
                                        finish();
                                    }
                                });
                    } else {
                        Toast.makeText(AddEditActivity.this, "Please enter a valid value", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            setTitle(R.string.add);
            button.setText(R.string.add);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String value = editText.getText().toString();
                    if (!TextUtils.isEmpty(value)) {
                        new UserRepo(getApplication())
                                .insert(new User(value));

                        /*INSERT DATA into DB*/
                        finish();
                    } else {
                        Toast.makeText(AddEditActivity.this, "Please enter a valid value", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
