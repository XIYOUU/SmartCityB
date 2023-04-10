package com.example.smartcityb.park;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smartcityb.Public.ApiServe;
import com.example.smartcityb.R;
import com.example.smartcityb.Utils.ConnUtil;
import com.example.smartcityb.parkBean.ParkHistoryBean;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkHistoryActivity extends AppCompatActivity {

    private ListView history_park;

    private Integer pageNum;
    private Integer pageSize;
    Integer p=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_history);
        Button add_page = (Button) findViewById(R.id.add_page);

        ImageView quit = (ImageView) findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        history_park = (ListView) findViewById(R.id.history_park);

        pageNum=1;
        pageSize=p*5;
        initHistoryPart(pageNum,pageSize);
        add_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p++;
                pageSize=p*5;
                initHistoryPart(pageNum,pageSize);
            }
        });


    }

    private void initHistoryPart(Integer pageNum,Integer pageSize) {
        ApiServe apiServe = ConnUtil.creServe2();
        Call<ParkHistoryBean> parkRecord = apiServe.getParkRecord(pageNum,pageSize);
        parkRecord.enqueue(new Callback<ParkHistoryBean>() {
            @Override
            public void onResponse(Call<ParkHistoryBean> call, Response<ParkHistoryBean> response) {
                history_park.setAdapter(new BaseAdapter() {
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
                        View v = LayoutInflater.from(ParkHistoryActivity.this).inflate(R.layout.layout_history_item, null);

                        TextView plateNumber = (TextView) v.findViewById(R.id.plateNumber);
                        TextView monetary = (TextView) v.findViewById(R.id.monetary);
                        TextView entryTime = (TextView) v.findViewById(R.id.entryTime);
                        TextView outTime = (TextView) v.findViewById(R.id.outTime);
                        TextView parkName = (TextView) v.findViewById(R.id.parkName);

                        plateNumber.setText("车牌号:"+response.body().getRows().get(position).getPlateNumber());
                        monetary.setText("收费金额:"+response.body().getRows().get(position).getMonetary());
                        entryTime.setText("入场时间:"+response.body().getRows().get(position).getEntryTime());
                        outTime.setText("出场时间:"+response.body().getRows().get(position).getOutTime());
                        parkName.setText("停车场名称:"+response.body().getRows().get(position).getParkName());
                        return v;
                    }
                });
            }

            @Override
            public void onFailure(Call<ParkHistoryBean> call, Throwable throwable) {

            }
        });
    }
}