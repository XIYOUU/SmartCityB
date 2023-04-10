package com.example.smartcityb.city;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcityb.Public.ApiServe;
import com.example.smartcityb.R;
import com.example.smartcityb.Utils.ConnUtil;
import com.example.smartcityb.cityBean.MetroLineBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MetroDetailActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private TextView detail_name;
    private MetroLineBean metroLineBean;
    private TextView first;
    private TextView end;
    private TextView startTime_endTime;
    private TextView stationsNumber;
    private TextView km;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro_detail);

        ImageView quit = (ImageView) findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        detail_name = (TextView) findViewById(R.id.detail_name);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        first = (TextView) findViewById(R.id.first);
        end = (TextView) findViewById(R.id.end);
        startTime_endTime = (TextView) findViewById(R.id.startTime_endTime);
        stationsNumber = (TextView) findViewById(R.id.stationsNumber);
        km = (TextView) findViewById(R.id.km);


        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
//        Log.e("id",String.valueOf(id));
        metro(id);



    }

    public void metro(Integer id){
        ApiServe apiServe = ConnUtil.creServe2();
        Call<MetroLineBean> metroLine = apiServe.getMetroLine(id);
        metroLine.enqueue(new Callback<MetroLineBean>() {



            @Override
            public void onResponse(Call<MetroLineBean> call, Response<MetroLineBean> response) {
                metroLineBean = new MetroLineBean();
                metroLineBean=response.body();
                first.setText("始发站："+metroLineBean.getData().getFirst());
                end.setText("终点站："+metroLineBean.getData().getEnd());
                startTime_endTime.setText("剩余时间：8");
                stationsNumber.setText("站数："+metroLineBean.getData().getStationsNumber());
                km.setText("剩余距离："+metroLineBean.getData().getKm());

//                recycler.setLayoutManager(new LinearLayoutManager(MetroDetailActivity.this));
//                recycler.setAdapter(new );
            }

            @Override
            public void onFailure(Call<MetroLineBean> call, Throwable throwable) {

            }
        });
    }
    public class metroAdapter extends RecyclerView.Adapter<metroAdapter.metroHolder>{
        @NonNull
        @Override
        public metroHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(MetroDetailActivity.this).inflate(R.layout.rec_metro_list, null);
            return new metroHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull metroHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class metroHolder extends RecyclerView.ViewHolder{

            public metroHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}