package com.example.smartcityb.FindWork;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.smartcityb.FindWorkBean.WorkListBean;
import com.example.smartcityb.IndexBean.IndexBannerBean;
import com.example.smartcityb.MainActivity;
import com.example.smartcityb.Public.ApiServe;
import com.example.smartcityb.R;
import com.example.smartcityb.Utils.ConnUtil;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindWorkFragment extends Fragment {

    private Banner banner;
    private View v;
    private Toolbar toolbar;
    private RecyclerView rec_hot_work;
    private RecyclerView rec_work_list;
    private WorkListBean workList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_find_work, null, false);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        banner = (Banner) v.findViewById(R.id.banner);
        rec_hot_work = (RecyclerView) v.findViewById(R.id.rec_hot_work);
        rec_work_list = (RecyclerView) v.findViewById(R.id.rec_work_list);



        toolbar.inflateMenu(R.menu.find_house_search_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
//                FragmentManager fm = getFragmentManager();
//                fm.beginTransaction().remove(FindWorkFragment.this).commit();
            }
        });

        initBann();
        initWorkList();
        return v;
    }

    //初始化轮播图
    public void initBann(){
        ApiServe apiServe = ConnUtil.creServe();
        Call<ResponseBody> rotationList = apiServe.getRotationList("2");
        rotationList.enqueue(new Callback<ResponseBody>() {

            private IndexBannerBean indexBannerBean;

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    Gson gson=new Gson();
                    indexBannerBean = gson.fromJson(string, IndexBannerBean.class);

                    banner.setAdapter(new BannerImageAdapter<IndexBannerBean.RowsBean>(indexBannerBean.getRows()) {
                        @Override
                        public void onBindView(BannerImageHolder holder, IndexBannerBean.RowsBean data, int position, int size) {
//                            Glide.with(holder.itemView)
//                                    .load(ConnUtil.getPath()+indexBannerBean.getRows().get(position).getAdvImg())
//                                    .into(holder.imageView);
                            Glide.with(getContext())
                                    .load(ConnUtil.getPath()+indexBannerBean.getRows().get(position).getAdvImg())
                                    .into(holder.imageView);
                        }

                    });
                    banner.setIndicator(new CircleIndicator(getActivity()));
                    banner.start();

                    banner.setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(Object data, int position) {
                            Toast.makeText(getActivity(), "position:"+position, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    //初始化职位列表
    public void initWorkList(){
        ApiServe apiServe = ConnUtil.creServe();
        Call<ResponseBody> professionList = apiServe.getProfessionList(MainActivity.Token);
        professionList.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    Gson gson=new Gson();
                    workList = gson.fromJson(string, WorkListBean.class);
                    rec_work_list.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rec_work_list.setAdapter(new workListAdapter());
                    rec_work_list.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
                } catch (IOException e) {


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });

    }
    public class workListAdapter extends RecyclerView.Adapter<workListAdapter.workListHolder>{

        @NonNull
        @Override
        public workListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.layout_find_house_work_list, null);

            return new workListHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull workListHolder holder, int position) {
            holder.tv_work_list.setText(workList.getRows().get(position).getProfessionName());
        }

        @Override
        public int getItemCount() {
            return workList.getRows().size();
        }

        public class workListHolder extends RecyclerView.ViewHolder {

            private final TextView tv_work_list;

            public workListHolder(@NonNull View itemView) {
                super(itemView);
                tv_work_list = (TextView) itemView.findViewById(R.id.tv_work_list);
            }
        }
    }
}