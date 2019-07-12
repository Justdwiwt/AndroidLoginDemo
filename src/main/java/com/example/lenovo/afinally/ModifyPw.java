package com.example.lenovo.afinally;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ModifyPw extends AppCompatActivity {

    private User user;

    private UsersSQLite usersSQLite;
    private SQLiteDatabase db;

    private TextView username;

    private Button btConfirm;
    private Button btCancel;

    private EditText oldPassword;
    private EditText newPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reSetPwd);

        Intent intent = this.getIntent();
        user = (User)intent.getSerializableExtra("user");

        usersSQLite = new UsersSQLite(ModifyPw.this,"student.db",null,1);
        db = usersSQLite.getWritableDatabase();

        username = (TextView)findViewById(R.id.text_modify_username);
        username.setText(user.getName());

        btConfirm = (Button)findViewById(R.id.button_confirm);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldPassword = (EditText)findViewById(R.id.text_modify_password);
                if (oldPassword.getText().toString().trim().equals(user.getPassword())){
                    newPassword = (EditText)findViewById(R.id.editTxt_psw_regist);
                    usersSQLite.updatePassword(user,newPassword.getText().toString().trim());
                    Intent intent = new Intent(ModifyPw.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        btCancel = (Button)findViewById(R.id.button_cancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModifyPw.this,Userinfo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
