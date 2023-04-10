package com.example.smartcityb.LivePayment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.smartcityb.R;

public class ManegeActivity extends AppCompatActivity {

    private Button btn_add_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manege);

        btn_add_group = (Button) findViewById(R.id.btn_add_group);
        btn_add_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManegeActivity.this, AddGroupActivity.class);
                startActivity(intent);
            }
        });
    }
}