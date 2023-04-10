package com.example.smartcityb.bus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smartcityb.Public.ApiServe;
import com.example.smartcityb.R;
import com.example.smartcityb.Utils.ConnUtil;
import com.example.smartcityb.busBean.BusLineBean;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusMainActivity extends AppCompatActivity {

    private ListView but_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_main);

        but_list = (ListView) findViewById(R.id.but_list);
        Button add_line = (Button) findViewById(R.id.add_line);
        add_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BusMainActivity.this,BusAddLineActivity.class));
            }
        });


        initBusList();
    }

    private void initBusList() {
        ApiServe apiServe = ConnUtil.creServe2();
        Call<BusLineBean> busLine = apiServe.getBusLine();
        busLine.enqueue(new Callback<BusLineBean>() {
            @Override
            public void onResponse(Call<BusLineBean> call, Response<BusLineBean> response) {
                but_list.setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return response.body().getRows().size();
                    }

                    @Override
                    public Object getItem(int position) {
                        return response.body().getRows().get(position);
                    }

                    @Override
                    public long getItemId(int position) {
                        return position;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View v = LayoutInflater.from(BusMainActivity.this).inflate(R.layout.list_bus, null);

                        TextView name = (TextView) v.findViewById(R.id.name);
                        TextView first = (TextView) v.findViewById(R.id.first);
                        TextView end = (TextView) v.findViewById(R.id.end);
                        TextView startTime = (TextView) v.findViewById(R.id.startTime);
                        TextView endTime = (TextView) v.findViewById(R.id.endTime);
                        TextView price = (TextView) v.findViewById(R.id.price);
                        TextView mileage = (TextView) v.findViewById(R.id.mileage);


                        name.setText("路线名称："+response.body().getRows().get(position).getName());
                        first.setText("起点："+response.body().getRows().get(position).getFirst());
                        end.setText("终点："+response.body().getRows().get(position).getEnd());
                        startTime.setText("运行起始时间："+response.body().getRows().get(position).getStartTime());
                        endTime.setText("运行终止时间："+response.body().getRows().get(position).getEnd());
                        price.setText("里程："+response.body().getRows().get(position).getParams());
                        mileage.setText("票价："+response.body().getRows().get(position).getMileage());
                        return v;
                    }
                });
            }

            @Override
            public void onFailure(Call<BusLineBean> call, Throwable throwable) {

            }
        });
    }
}