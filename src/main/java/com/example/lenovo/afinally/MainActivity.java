package com.example.lenovo.afinally;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_register;
    private Button bt_login;

    private EditText text_register_username;
    private EditText text_register_phone;
    private EditText text_register_password;
    private String name, phone, psw;
    private UsersSQLite usersSQLite;
    private SQLiteDatabase db;
    private User user;

    private EditText login_username;
    private EditText login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_register = (Button) findViewById(R.id.btn_main_register);
        bt_register.setOnClickListener(this);
        bt_login = (Button) findViewById(R.id.btn_main_login);
        bt_login.setOnClickListener(this);

        usersSQLite = new UsersSQLite(MainActivity.this, "student.db", null, 1);
        db = usersSQLite.getWritableDatabase();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_main_register:
                AlertDialog builder = new AlertDialog.Builder(MainActivity.this).create();
                view = View.inflate(MainActivity.this, R.layout.registerUser, null);
                builder.setView(view);
                text_register_username = (EditText) view.findViewById(R.id.text_register_username);
                text_register_phone = (EditText) view.findViewById(R.id.text_register_phone);
                text_register_password = (EditText) view.findViewById(R.id.text_register_password);
                builder.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        name = text_register_username.getText().toString().trim();
                        phone = text_register_phone.getText().toString().trim();
                        psw = text_register_password.getText().toString().trim();
                        user = new User(name, phone, psw);
                        usersSQLite.insert(user);
                    }
                });
                builder.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
                break;
            case R.id.btn_main_login:
                login_username = (EditText) findViewById(R.id.text_main_username);
                login_password = (EditText) findViewById(R.id.text_main_password);
                user = new User(login_username.getText().toString().trim(), login_password.getText().toString().trim());
                Boolean flag = usersSQLite.find(user);
                if (flag) {
                    Intent intent = new Intent(MainActivity.this, Userinfo.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", user);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "账号或密码不正确", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
