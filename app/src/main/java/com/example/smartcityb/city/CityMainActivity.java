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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartcityb.Public.ApiServe;
import com.example.smartcityb.R;
import com.example.smartcityb.Utils.ConnUtil;
import com.example.smartcityb.cityBean.AllMetroLine;
import com.example.smartcityb.cityBean.CityLineBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityMainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    CityLineBean cityLineBeans;
    private ImageView all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_main);

        recycler = (RecyclerView) findViewById(R.id.recycler);
        all = (ImageView) findViewById(R.id.all);



        ImageView quit = (ImageView) findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CityMainActivity.this, MetroTotalActivity.class));
            }
        });



        initCityLine();
    }

    public void initCityLine(){
        ApiServe apiServe = ConnUtil.creServe2();
        Call<CityLineBean> jianguo = apiServe.getMetroList("建国门");
        jianguo.enqueue(new Callback<CityLineBean>() {
            @Override
            public void onResponse(Call<CityLineBean> call, Response<CityLineBean> response) {
                cityLineBeans=response.body();
                Log.e("ddd",String.valueOf(cityLineBeans.getData().size()));
                recycler.setAdapter(new mertoAdapter());
                recycler.setLayoutManager(new LinearLayoutManager(CityMainActivity.this));

            }

            @Override
            public void onFailure(Call<CityLineBean> call, Throwable throwable) {

            }
        });
    }
    public class mertoAdapter extends RecyclerView.Adapter<mertoAdapter.metroHolder>{
        @NonNull
        @Override
        public metroHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(CityMainActivity.this).inflate(R.layout.recyle_city_line_item, null);
            return new metroHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull metroHolder holder, int position) {
            holder.lineName.setText("地铁路线:"+cityLineBeans.getData().get(position).getLineName());
            holder.nextStep.setText("下一站:"+cityLineBeans.getData().get(position).getNextStep().getName());
            holder.reachTime.setText("到达本站时长:"+cityLineBeans.getData().get(position).getReachTime()+"分钟");
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CityMainActivity.this, MetroDetailActivity.class);
                    intent.putExtra("id",cityLineBeans.getData().get(position).getLineId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return cityLineBeans.getData().size();
        }

        public class metroHolder extends RecyclerView.ViewHolder{

            private final TextView lineName;
            private final TextView nextStep;
            private final TextView reachTime;
            private final LinearLayout ll;

            public metroHolder(@NonNull View itemView) {
                super(itemView);
                lineName = (TextView) itemView.findViewById(R.id.lineName);
                nextStep = (TextView) itemView.findViewById(R.id.nextStep);
                reachTime = (TextView) itemView.findViewById(R.id.reachTime);
                ll = (LinearLayout) itemView.findViewById(R.id.ll);
            }
        }
    }
}