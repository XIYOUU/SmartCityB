package com.example.smartcityb.violation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.smartcityb.Public.ApiServe;
import com.example.smartcityb.R;
import com.example.smartcityb.Utils.ConnUtil;

public class ViolationRecordActivity extends AppCompatActivity {

    private ListView violation_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violation_record);

        violation_list = (ListView) findViewById(R.id.violation_list);


        initQuery();
    }

    private void initQuery() {
        ApiServe apiServe = ConnUtil.creServe2();

    }

}