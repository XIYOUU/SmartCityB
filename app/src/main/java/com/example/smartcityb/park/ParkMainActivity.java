package com.example.smartcityb.park;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smartcityb.Public.ApiServe;
import com.example.smartcityb.R;
import com.example.smartcityb.Utils.ConnUtil;
import com.example.smartcityb.parkBean.ParkBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkMainActivity extends AppCompatActivity {

    private ListView list_part_item;
    private Integer pageNum;
    private Integer pageSize;
    private Button add_page;
    Integer p=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_main);

        list_part_item = (ListView) findViewById(R.id.list_part_item);
        add_page = (Button) findViewById(R.id.add_page);

        ImageView quit = (ImageView) findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btn_show_list = (Button) findViewById(R.id.btn_show_list);
        btn_show_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParkMainActivity.this,ParkHistoryActivity.class));
            }
        });


        pageNum=1;
        pageSize=p*5;
        initPartItem(pageNum,pageSize);
        add_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p++;
                pageSize=p*5;
                Log.e("pageNum",String.valueOf(pageNum));
                Log.e("pageSize",String.valueOf(pageSize));
                initPartItem(pageNum,pageSize);
            }
        });

    }

    private void initPartItem(Integer pageNum,Integer pageSize) {

        ApiServe apiServe = ConnUtil.creServe2();
        Call<ParkBean> parkList = apiServe.getParkList(pageNum,pageSize);
        parkList.enqueue(new Callback<ParkBean>() {
            @Override
            public void onResponse(Call<ParkBean> call, Response<ParkBean> response) {
                list_part_item.setAdapter(new BaseAdapter() {
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
                        View v = LayoutInflater.from(ParkMainActivity.this).inflate(R.layout.layout_part_item, null);
                        TextView parkName = (TextView) v.findViewById(R.id.parkName);
                        TextView vacancy = (TextView) v.findViewById(R.id.vacancy);
                        TextView address = (TextView) v.findViewById(R.id.address);
                        TextView rates = (TextView) v.findViewById(R.id.rates);
                        TextView distance = (TextView) v.findViewById(R.id.distance);

                        parkName.setText("停车场名："+response.body().getRows().get(position).getParkName());
                        vacancy.setText("空位数量："+response.body().getRows().get(position).getVacancy());
                        address.setText("地址："+response.body().getRows().get(position).getAddress());
                        rates.setText("收费费率："+response.body().getRows().get(position).getRates());
                        distance.setText("距离："+response.body().getRows().get(position).getDistance());

                        return v;
                    }
                });
                list_part_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent=new Intent(ParkMainActivity.this, ParkDetailActivity.class);
                        intent.putExtra("parkName",response.body().getRows().get(position).getParkName());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<ParkBean> call, Throwable throwable) {

            }
        });
    }
}