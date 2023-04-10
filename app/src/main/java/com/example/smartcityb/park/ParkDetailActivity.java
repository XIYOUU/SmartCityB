package com.example.smartcityb.park;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcityb.Public.ApiServe;
import com.example.smartcityb.R;
import com.example.smartcityb.Utils.ConnUtil;
import com.example.smartcityb.parkBean.ParkBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkDetailActivity extends AppCompatActivity {

    private TextView parkName;
    private TextView address;
    private TextView distance;
    private TextView open;
    private TextView rates;
    private TextView vacancy;
    private TextView priceCaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_detail);

        parkName = (TextView) findViewById(R.id.parkName);
        address = (TextView) findViewById(R.id.address);
        distance = (TextView) findViewById(R.id.distance);
        open = (TextView) findViewById(R.id.open);
        rates = (TextView) findViewById(R.id.rates);
        vacancy = (TextView) findViewById(R.id.vacancy);
        priceCaps = (TextView) findViewById(R.id.priceCaps);

        ImageView quit = (ImageView) findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        String parkNameV = intent.getStringExtra("parkName");

        initPartDetail(parkNameV);
    }

    private void initPartDetail(String parkNameV) {
        ApiServe apiServe = ConnUtil.creServe2();
        Call<ParkBean> parkDetail = apiServe.getParkDetail(parkNameV);
        parkDetail.enqueue(new Callback<ParkBean>() {
            @Override
            public void onResponse(Call<ParkBean> call, Response<ParkBean> response) {
                parkName.setText("停车场名称:"+response.body().getRows().get(0).getParkName());
                address.setText("地址:"+response.body().getRows().get(0).getAddress());
                distance.setText("距离:"+response.body().getRows().get(0).getDistance());
                if(response.body().getRows().get(0).getOpen().equals("Y")){
                    open.setText("是否对外开放:"+"对外开放");
                }else{
                    open.setText("是否对外开放:"+"不对外开放");
                }
                rates.setText("车位信息:停车费："+response.body().getRows().get(0).getRates()+"/小时");
                vacancy.setText("剩余车位："+response.body().getRows().get(0).getVacancy());
                priceCaps.setText("收费参考:"+"每小时"+response.body().getRows().get(0).getRates()+"元,"+
                        "最高"+response.body().getRows().get(0).getPriceCaps()+"元/每天");
            }

            @Override
            public void onFailure(Call<ParkBean> call, Throwable throwable) {

            }
        });
    }
}