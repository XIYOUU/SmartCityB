package com.example.smartcityb.FindWork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.smartcityb.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FindWorkMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_work_main);

        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navView.getMenu())
//                .build();

        NavController navController = Navigation.findNavController(this, R.id.fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
}