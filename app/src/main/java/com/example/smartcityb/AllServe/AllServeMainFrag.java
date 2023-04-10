package com.example.smartcityb.AllServe;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.smartcityb.FindWork.FindWorkMainActivity;
import com.example.smartcityb.Index.IndexFrag;
import com.example.smartcityb.IndexBean.IndexServiceListBean;
import com.example.smartcityb.LivePayment.LivePaymentMainActivity;
import com.example.smartcityb.Public.ApiServe;
import com.example.smartcityb.R;
import com.example.smartcityb.Utils.ConnUtil;
import com.example.smartcityb.bus.BusMainActivity;
import com.example.smartcityb.city.CityMainActivity;
import com.example.smartcityb.move.MoveMainActivity;
import com.example.smartcityb.park.ParkMainActivity;
import com.example.smartcityb.violation.ViolationMainActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AllServeMainFrag extends Fragment {

    private View v;
    private List<IndexServiceListBean.RowsBean> list;
    private IndexServiceListBean indexServiceListBean;
    private RecyclerView rv_serve_type;
    private List<String> listType;
    private IndexServiceListBean indexServiceListBean1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.frag_all_server, null, false);

        Toolbar toolbar2 = (Toolbar) v.findViewById(R.id.toolbar2);
        toolbar2.inflateMenu(R.menu.serve_search_menu);

        initServiceList();

        return v;
    }

    //初始化系统服务(列表)
    public void initServiceList() {
        ApiServe apiServe = ConnUtil.creServe();
        Call<ResponseBody> serviceList = apiServe.getServiceList();
        serviceList.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    Gson gson = new Gson();
                    indexServiceListBean = gson.fromJson(string, IndexServiceListBean.class);

                    list = indexServiceListBean.getRows();
                    listType = new ArrayList<String>();
//                    Log.e("d2", String.valueOf(listType.size()));
                    int p = 0;
//                    listType.add(indexServiceListBean.getRows().get(0).getServiceType());
                    //去掉重复元素
                    for (int i = 0; i < indexServiceListBean.getRows().size(); i++) {
                        //如果重复数组在不重复数组里面找到就break;
                        if (!listType.contains(list.get(i).getServiceType())) {
//                            Log.e("dddd",String.valueOf(list.get(i).getServiceType()));
                            listType.add(list.get(i).getServiceType());
                        }
                    }
                    rv_serve_type = (RecyclerView) v.findViewById(R.id.rv_serve_type);
                    rv_serve_type.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv_serve_type.setAdapter(new serveAdapter());
                } catch (IOException e) {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }
    public class serveAdapter extends RecyclerView.Adapter<serveAdapter.serveHolder> {

        public class serveHolder extends RecyclerView.ViewHolder {
//            private final ImageView iv_serve_pic;
            private final TextView tv_serve_name;
            private final RelativeLayout rl_server_item;

            public serveHolder(@NonNull View itemView) {
                super(itemView);
//                iv_serve_pic = (ImageView) itemView.findViewById(R.id.iv_serve_pic);
                tv_serve_name = (TextView) itemView.findViewById(R.id.tv_serve_name);
                rl_server_item = (RelativeLayout) itemView.findViewById(R.id.rl_server_item);
            }
        }


        @NonNull
        @Override
        public serveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.layout_serve_line_item, null);
            return new serveHolder(v);
        }


        @Override
        public void onBindViewHolder(@NonNull serveHolder holder, int position) {
//            Glide.with(holder.itemView)
//                    .load(ConnUtil.getPath() + indexServiceListBean.getRows().get(position).getImgUrl())
//                    .into(holder.iv_serve_pic);
            holder.tv_serve_name.setText(listType.get(position));

            initServiceGrid(listType.get(0));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getActivity(), "position:"+position, Toast.LENGTH_SHORT).show();
                    Log.e("ddd", "position:" + position);

                    initServiceGrid(listType.get(position));
                }
            });

        }

        @Override
        public int getItemCount() {
            return listType.size();
        }


    }


    //初始化对应的服务(网格)
    public void initServiceGrid(String type) {
        ApiServe apiServe = ConnUtil.creServe();
        Call<ResponseBody> serviceType = apiServe.getServiceList(type);
        serviceType.enqueue(new Callback<ResponseBody>() {


            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    Gson gson = new Gson();
                    indexServiceListBean1 = gson.fromJson(string, IndexServiceListBean.class);

                    RecyclerView rv_serve_type_all = (RecyclerView) v.findViewById(R.id.rv_serve_type_all);
                    rv_serve_type_all.setLayoutManager(new GridLayoutManager(getActivity(), 4));
                    rv_serve_type_all.setAdapter(new serveTypeAdapter());
                } catch (IOException e) {


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }
    public class serveTypeAdapter extends RecyclerView.Adapter<serveTypeAdapter.serveTypeHolder> {


        public class serveTypeHolder extends RecyclerView.ViewHolder {


            private final RelativeLayout rl_server_item;
            private final ImageView iv_serve_pic;
            private final TextView tv_serve_name;

            public serveTypeHolder(@NonNull View itemView) {
                super(itemView);

                rl_server_item = (RelativeLayout) itemView.findViewById(R.id.rl_server_item);
                iv_serve_pic = (ImageView) itemView.findViewById(R.id.iv_serve_pic);
                tv_serve_name = (TextView) itemView.findViewById(R.id.tv_serve_name);
            }
        }


        @NonNull
        @Override
        public serveTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(getActivity()).inflate(R.layout.layout_serve_item, null);
            return new serveTypeHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull serveTypeHolder holder, int position) {
            Glide.with(getActivity())
                    .load(ConnUtil.getPath() + indexServiceListBean1.getRows().get(position).getImgUrl())
                    .into(holder.iv_serve_pic);
            holder.tv_serve_name.setText(indexServiceListBean1.getRows().get(position).getServiceName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (indexServiceListBean1.getRows().get(position).getServiceName()){
                        case "找工作":{
                            Intent intent=new Intent(getActivity(),FindWorkMainActivity.class);
                            startActivity(intent);
                            break;
                        }
                        case "生活缴费":{
                            Intent intent=new Intent(getActivity(), LivePaymentMainActivity.class);
                            startActivity(intent);
                            break;
                        }
                        case "停哪儿":{
                            Intent intent=new Intent(getActivity(), ParkMainActivity.class);
                            startActivity(intent);
                            break;
                        }
                        case "智慧巴士":{
                            Intent intent=new Intent(getActivity(), BusMainActivity.class);
                            startActivity(intent);
                            break;
                        }
                        case "智慧交管":{
                            Intent intent=new Intent(getActivity(), ViolationMainActivity.class);
                            startActivity(intent);
                            break;
                        }
                        case "城市地铁":{
                            Intent intent=new Intent(getActivity(), CityMainActivity.class);
                            startActivity(intent);
                            break;
                        }

                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return indexServiceListBean1.getRows().size();
        }
    }
}