package com.android.llc.listmodule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.llc.listmodule.facebook.timeline.TimelineFragment;

public class MainActivity extends AppCompatActivity {
    RecyclerView recycle_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_time, new TimelineFragment()).commit();


    }
}
