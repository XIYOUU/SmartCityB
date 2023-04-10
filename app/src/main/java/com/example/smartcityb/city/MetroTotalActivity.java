package com.example.smartcityb.city;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smartcityb.Public.ApiServe;
import com.example.smartcityb.R;
import com.example.smartcityb.Utils.ConnUtil;
import com.example.smartcityb.cityBean.AllMetroLine;
import com.example.smartcityb.cityBean.MetroLinePic;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MetroTotalActivity extends AppCompatActivity {

    private ListView list_line;
    private ImageView all_line_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro_total);

        list_line = (ListView) findViewById(R.id.list_line);
        all_line_pic = (ImageView) findViewById(R.id.all_line_pic);


        initLine();
        initLinePic();
    }
    public void initLine(){
        ApiServe apiServe = ConnUtil.creServe2();
        Call<AllMetroLine> metroLineList = apiServe.getMetroLineList();
        metroLineList.enqueue(new Callback<AllMetroLine>() {
            @Override
            public void onResponse(Call<AllMetroLine> call, Response<AllMetroLine> response) {
                list_line.setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return response.body().getData().size();
                    }

                    @Override
                    public Object getItem(int position) {
                        return response.body().getData().get(position);
                    }

                    @Override
                    public long getItemId(int position) {
                        return position;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View v = LayoutInflater.from(MetroTotalActivity.this).inflate(R.layout.list_all_line, null);
                        TextView line_name = (TextView) v.findViewById(R.id.line_name);
                        LinearLayout line = (LinearLayout) v.findViewById(R.id.line);
                        TextView line_color = (TextView) findViewById(R.id.line_color);

                        line_name.setText(response.body().getData().get(position).getLineName());
//                        line_color.setBackgroundColor(Color.parseColor("#FFFF00"));
//                        line_color.setBackgroundColor(Color.parseColor("#FFFF00"));
                        return v;
                    }
                });
            }

            @Override
            public void onFailure(Call<AllMetroLine> call, Throwable throwable) {

            }
        });
    }

    public void initLinePic(){
        ApiServe apiServe = ConnUtil.creServe2();
        Call<MetroLinePic> metroCity = apiServe.getMetroCity();
        metroCity.enqueue(new Callback<MetroLinePic>() {
            @Override
            public void onResponse(Call<MetroLinePic> call, Response<MetroLinePic> response) {
                Glide.with(MetroTotalActivity.this)
                        .load(ConnUtil.getPath()+response.body().getData().getImgUrl())
                        .into(all_line_pic);
            }

            @Override
            public void onFailure(Call<MetroLinePic> call, Throwable throwable) {

            }
        });
    }
}