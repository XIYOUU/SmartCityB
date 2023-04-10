package com.example.smartcityb.LivePayment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcityb.MainActivity;
import com.example.smartcityb.Public.ApiServe;
import com.example.smartcityb.R;
import com.example.smartcityb.Utils.ConnUtil;
import com.example.smartcityb.livePaymentBean.LivingBillBean;
import com.example.smartcityb.livePaymentBean.UserInfoBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EleFreeActivity extends AppCompatActivity {

    private TextView debalance;
    private TextView balance;
    private TextView address;
    private TextView ownerName;
    private TextView paymentNo;
    private TextView chargeUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ele_free);

        ImageView quit = (ImageView) findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        chargeUnit = (TextView) findViewById(R.id.chargeUnit);
        paymentNo = (TextView) findViewById(R.id.paymentNo);
        ownerName = (TextView) findViewById(R.id.ownerName);
        address = (TextView) findViewById(R.id.address);
        balance = (TextView) findViewById(R.id.balance);
        debalance = (TextView) findViewById(R.id.debalance);

        initEle();
        inita1();
    }
    public void initEle(){
        ApiServe apiServe = ConnUtil.creServe2();
        Call<UserInfoBean> userInfo = apiServe.getUserInfo(MainActivity.Token);
        userInfo.enqueue(new Callback<UserInfoBean>() {
            @Override
            public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                if(response.body().getUser().getBalance()>=0){
                    balance.setText("当前可余额:"+response.body().getUser().getBalance()+"");
                    debalance.setText("当前欠费余额:"+"0");
                }else{
                    balance.setText("当前可余额:"+"0");
                    debalance.setText("当前欠费余额:"+response.body().getUser().getBalance()+"");
                }
            }

            @Override
            public void onFailure(Call<UserInfoBean> call, Throwable throwable) {

            }
        });


    }

    public void inita1(){
        ApiServe apiServe1 = ConnUtil.creServe2();
        Call<LivingBillBean> livingBill = apiServe1.getLivingBill(MainActivity.Token,"15670226","3");
        livingBill.enqueue(new Callback<LivingBillBean>() {
            @Override
            public void onResponse(Call<LivingBillBean> call, Response<LivingBillBean> response) {
                chargeUnit.setText("缴费单位："+response.body().getData().getChargeUnit());
                paymentNo.setText("缴费户号："+response.body().getData().getPaymentNo());
                address.setText("住址信息："+response.body().getData().getAddress());
            }

            @Override
            public void onFailure(Call<LivingBillBean> call, Throwable throwable) {

            }
        });
    }
}