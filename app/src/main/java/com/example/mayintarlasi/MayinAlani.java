package com.example.mayintarlasi;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// TODO: Kullanılmadı
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MayinAlani extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.mayinalani, container, false);
    }
}
