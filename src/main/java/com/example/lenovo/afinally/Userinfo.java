package com.example.lenovo.afinally;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Userinfo extends AppCompatActivity {

    private User user;
    private UsersSQLite usersSQLite;
    private SQLiteDatabase db;
    private TextView nickname;
    private TextView telephone;
    private Button btUserAll;
    private Button btModify;
    private String[] usernameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userData);
        nickname = (TextView) findViewById(R.id.text_nickname);
        telephone = (TextView) findViewById(R.id.text_telephone);
        // 显示所有用户/删除所有用户
        btUserAll = (Button) findViewById(R.id.bt_user_all);
        btUserAll.setOnClickListener(t);
        // 修改密码
        btModify = (Button) findViewById(R.id.bt_modify_password);
        btModify.setOnClickListener(t);
        // 获得当前登录用户信息
        Intent intent = this.getIntent();
        user = (User) intent.getSerializableExtra("user");
        nickname.setText(user.getName());
        telephone.setText(user.getPhone());
        // 创建dbSQLite对象，会自动调用onCreate(),创建数据库与数据库表
        usersSQLite = new UsersSQLite(Userinfo.this, "student.db", null, 1);
        db = usersSQLite.getWritableDatabase();  // 打开数据库
    }

    View.OnClickListener t = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment f = null;
            switch (view.getId()) {
                case R.id.bt_user_all:
                    if (btUserAll.getText().toString().trim().equals("所有用户")) {
                        btUserAll.setText("删除所有用户");
                        final ListView listView = getListView();
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                usersSQLite.deleteUser(usernameList[i]);
                                Toast.makeText(Userinfo.this, usernameList[i] + "删除成功", Toast.LENGTH_SHORT).show();
                                getListView();
                            }
                        });
                        f = new UserList();
                        ft.replace(R.id.fragment, f);
                        ft.commit();
                    } else {
                        usersSQLite.delete();
                        Intent intent = new Intent(Userinfo.this, MainActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.bt_modify_password:
                    Intent intent = new Intent(Userinfo.this, ModifyPw.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", user);
                    intent.putExtras(bundle);
                    startActivity(intent);
            }
        }
    };

    @NonNull
    public ListView getListView() {
        List<User> userList = usersSQLite.findAll();
        usernameList = new String[userList.size()];
        int i = 0;
        for (User user : userList) {
            usernameList[i++] = user.getName();
        }
        MyBaseAdapter adapter = new MyBaseAdapter();
        final ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
        return listView;
    }

    class MyBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return usernameList.length;
        }

        @Override
        public Object getItem(int i) {
            return usernameList[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            @SuppressLint("ViewHolder") View view = View.inflate(Userinfo.this, R.layout.listViewStyle, null);
            TextView mtextView = (TextView) view.findViewById(R.id.tv_list_user);
            mtextView.setText(usernameList[i]);
            return view;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}


