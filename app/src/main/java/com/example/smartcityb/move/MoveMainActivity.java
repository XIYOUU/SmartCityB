package com.example.smartcityb.move;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.example.smartcityb.R;

public class MoveMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_main);


        TabHost tabhost = (TabHost) findViewById(android.R.id.tabhost);
        tabhost.setup();


        tabhost.addTab(tabhost.newTabSpec("1").setIndicator("自助移车").setContent(new view1()));
        tabhost.addTab(tabhost.newTabSpec("2").setIndicator("历史记录").setContent(new view2()));
        ImageView quit = (ImageView) findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public class view1 implements TabHost.TabContentFactory{
        @Override
        public View createTabContent(String tag) {
            View v = LayoutInflater.from(MoveMainActivity.this).inflate(R.layout.fragment_auto, null);
            return v;
        }
    }
    public class view2 implements TabHost.TabContentFactory{
        @Override
        public View createTabContent(String tag) {
            View v = LayoutInflater.from(MoveMainActivity.this).inflate(R.layout.fragment_record, null);
            return v;
        }
    }
}