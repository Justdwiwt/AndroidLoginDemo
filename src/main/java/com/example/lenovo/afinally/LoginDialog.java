package com.example.lenovo.afinally;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class LoginDialog extends Dialog {

    private Button btn_cancel;
    private Button btn_confirm;

    private EditText text_register_username;
    private EditText text_register_phone;
    private EditText text_register_password;
    private String name,phone,psw;

    private User user;

    public LoginDialog(Context context){
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.registerUser);

        text_register_username = (EditText)findViewById(R.id.text_register_username);
        text_register_phone = (EditText)findViewById(R.id.text_register_phone);
        text_register_password = (EditText)findViewById(R.id.text_register_password);
        btn_confirm = (Button)findViewById(R.id.button_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = text_register_username.getText().toString().trim();
                phone = text_register_phone.getText().toString().trim();
                psw = text_register_password.getText().toString().trim();
                user = new User(name,phone,psw);
                dismiss();
            }
        });
        btn_cancel = (Button)findViewById(R.id.button_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
