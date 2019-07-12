package com.example.lenovo.afinally;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UserList extends Fragment {

    @SuppressWarnings("UnnecessaryLocalVariable")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listView, container, false);
        return view;
    }

}
