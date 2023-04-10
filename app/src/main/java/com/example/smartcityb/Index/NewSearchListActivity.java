package com.example.smartcityb.Index;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smartcityb.Public.ApiServe;
import com.example.smartcityb.PublicBean.NewSortListBean;
import com.example.smartcityb.R;
import com.example.smartcityb.Utils.ConnUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewSearchListActivity extends AppCompatActivity {

    private ListView lv_new_search_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_search_list);

        lv_new_search_content = (ListView) findViewById(R.id.lv_new_search_content);
        Intent intent = getIntent();
        String query = intent.getStringExtra("query");
        doSearch(query);
    }

    public void doSearch(String query) {
        ApiServe apiServe = ConnUtil.creServe2();
        Call<NewSortListBean> pressListQuery = apiServe.getPressListQuery(query);
        pressListQuery.enqueue(new Callback<NewSortListBean>() {
            @Override
            public void onResponse(Call<NewSortListBean> call, Response<NewSortListBean> response) {
                lv_new_search_content.setAdapter(new BaseAdapter() {
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
                        View v = LayoutInflater.from(NewSearchListActivity.this).inflate(R.layout.layout_new_search_item, null);
                        TextView tv_search_content = (TextView) v.findViewById(R.id.tv_search_content);
                        tv_search_content.setText(response.body().getRows().get(position).getTitle());
                        return v;
                    }
                });
            }

            @Override
            public void onFailure(Call<NewSortListBean> call, Throwable throwable) {

            }
        });
    }
}