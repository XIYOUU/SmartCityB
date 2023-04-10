package com.example.smartcityb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.smartcityb.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static String Token;
    private ViewPager view_pager;

    private static boolean isFirstUse;
    List<View> views;
    private ImageView spot1;
    private ImageView spot2;
    private ImageView spot3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        view_pager = (ViewPager) findViewById(R.id.view_pager);
        spot1 = (ImageView) findViewById(R.id.spot1);
        spot2 = (ImageView) findViewById(R.id.spot2);
        spot3 = (ImageView) findViewById(R.id.spot3);


        View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.guide3, null);
        Button enter = (Button) v.findViewById(R.id.enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                Log.e("ddd","ddd1");
            }
        });

        SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        isFirstUse=sharedPreferences.getBoolean("isFirstUse",true);
        if (isFirstUse==false) {
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }else {
            edit.putBoolean("isFirstUse",false);
            edit.commit();
        }

        views=new ArrayList<>();
        LayoutInflater from = LayoutInflater.from(this);

        views.add(from.inflate(R.layout.guide1,null));
        views.add(from.inflate(R.layout.guide2,null));
        views.add(from.inflate(R.layout.guide3,null));
        initGuide();
    }
    public void initGuide(){
        view_pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View v=views.get(position);
                container.addView(v);
                if(position==2){
                    Button enter = (Button) v.findViewById(R.id.enter);
                    enter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            Log.e("ddd","ddd1");
                        }
                    });
                }
                return v;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//                super.destroyItem(container, position, object);
                container.removeView(views.get(position));
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view==object;
            }
        });
        view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                spot1.setImageResource(R.drawable.spot);
                spot2.setImageResource(R.drawable.spot);
                spot3.setImageResource(R.drawable.spot);
                switch (position){
                    case 0:{
                        spot1.setImageResource(R.drawable.spot2);
                        break;
                    }
                    case 1:{
                        spot2.setImageResource(R.drawable.spot2);
                        break;
                    }
                    case 2:{
                        spot3.setImageResource(R.drawable.spot2);
                        break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}