package com.example.smartcityb.Index;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smartcityb.IndexBean.IndexBannerBean;
import com.example.smartcityb.IndexBean.IndexServiceListBean;
import com.example.smartcityb.Public.ApiServe;
import com.example.smartcityb.PublicBean.NewSortBean;
import com.example.smartcityb.PublicBean.NewSortListBean;
import com.example.smartcityb.R;
import com.example.smartcityb.Utils.ConnUtil;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IndexFrag extends Fragment implements TabHost.TabContentFactory {
    private IndexServiceListBean indexServiceListBean;
    private View v;
    private Banner banner;
    private List<IndexServiceListBean.RowsBean> list;
    private Toolbar toolbar2;
    private NavController navController;
    private SearchView search_bar;
    private ListView lv_new_search;
GridLayout gd_services;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.frag_index, container, false);
        gd_services=(GridLayout)v.findViewById(R.id.gd_services);
        banner = (Banner) v.findViewById(R.id.banner);
        toolbar2 = (Toolbar) v.findViewById(R.id.toolbar2);
        toolbar2.inflateMenu(R.menu.search_menu);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_nav_main1);
        lv_new_search = (ListView) v.findViewById(R.id.lv_new_search);

        search_bar = (SearchView) v.findViewById(R.id.search_bar);
        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("ddd","搜索按钮按下");
                Toast.makeText(getActivity(), "搜索按钮按下", Toast.LENGTH_SHORT).show();
                if(query.isEmpty()){
                    Toast.makeText(getActivity(), "内容为空", Toast.LENGTH_SHORT).show();
//                    lv_new_search.setVisibility(View.GONE);
                }else{
                    Toast.makeText(getActivity(), "内容不为空", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(),NewSearchListActivity.class);
                    intent.putExtra("query",query);
                    startActivity(intent);



//                    lv_new_search.setVisibility(View.VISIBLE);
//                    doSearch();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("ddd","搜索内容改变");
                Toast.makeText(getActivity(), "搜索内容改变", Toast.LENGTH_SHORT).show();
                lv_new_search.setVisibility(View.GONE);
                return false;
            }
        });

        initBann();
        initServiceList();
        initNewTab();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.search_menu, menu);
        toolbar2.inflateMenu(R.menu.search_menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) searchItem.getActionView();

    }

    //初始化系统服务
    public void initServiceList(){
        ApiServe apiServe = ConnUtil.creServe();
        Call<ResponseBody> serviceList = apiServe.getServiceList();
        serviceList.enqueue(new Callback<ResponseBody>() {

            private RecyclerView rv_serve;

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    Gson gson=new Gson();
                    indexServiceListBean = gson.fromJson(string, IndexServiceListBean.class);

                    list=indexServiceListBean.getRows();
                    Collections.sort(list, new Comparator<IndexServiceListBean.RowsBean>() {
                        @Override
                        public int compare(IndexServiceListBean.RowsBean o1, IndexServiceListBean.RowsBean o2) {
                            return -(o1.getId()-o2.getId());
                        }
                    });
                    rv_serve = (RecyclerView) v.findViewById(R.id.rv_serve);
                    rv_serve.setLayoutManager(new GridLayoutManager(getActivity(),5));
                    rv_serve.setAdapter(new serveAdapter());

                   for(int i=0;i<10;i++){
                       gd_services.addView(buildGridItemView(i));
                   }
                } catch (IOException e) {


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }
    private View buildGridItemView(int position){
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.layout_serve_item2, null,false);
        v.setMinimumWidth(width/5);
        ImageView iv_serve_pic = (ImageView) v.findViewById(R.id.iv_serve_pic);
        TextView tv_serve_name = (TextView) v.findViewById(R.id.tv_serve_name);
        RelativeLayout rl_server_item = (RelativeLayout) v.findViewById(R.id.rl_server_item);
        if(position==9){
            iv_serve_pic.getResources().getDrawable(R.drawable.more_serve);
            tv_serve_name.setText("更多服务");
            rl_server_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("ddd","position:"+position);
                    navController.navigate(R.id.action_indexFrag_to_allServerFrag);
                }
            });
        }else{
            Glide.with(v)
                    .load(ConnUtil.getPath()+indexServiceListBean.getRows().get(position).getImgUrl())
                    .into(iv_serve_pic);
            tv_serve_name.setText(indexServiceListBean.getRows().get(position).getServiceName());
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getActivity(), "position:"+position, Toast.LENGTH_SHORT).show();
                    Log.e("ddd","position:"+position);
                    switch (list.get(position).getServiceName()){
                        case "找工作":{
                            navController.navigate(R.id.action_indexFrag_to_findWorkMainActivity);
                        }
                        case "生活缴费":{
                            navController.navigate(R.id.action_indexFrag_to_livePaymentMainActivity);
                        }
                    }
                }
            });
        }
        return v;
    }





    public class serveAdapter extends RecyclerView.Adapter<serveAdapter.serveHolder>{

        public class serveHolder extends RecyclerView.ViewHolder{
            private final ImageView iv_serve_pic;
            private final TextView tv_serve_name;
            private final RelativeLayout rl_server_item;

            public serveHolder(@NonNull View itemView) {
                super(itemView);
                iv_serve_pic = (ImageView) itemView.findViewById(R.id.iv_serve_pic);
                tv_serve_name = (TextView) itemView.findViewById(R.id.tv_serve_name);
                rl_server_item = (RelativeLayout) itemView.findViewById(R.id.rl_server_item);
            }
        }


        @NonNull
        @Override
        public serveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.layout_serve_item, null);

            return new serveHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull serveHolder holder, int position) {
            if(position==9){
                holder.iv_serve_pic.getResources().getDrawable(R.drawable.more_serve);
                holder.tv_serve_name.setText("更多服务");
                holder.rl_server_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("ddd","position:"+position);
                        navController.navigate(R.id.action_indexFrag_to_allServerFrag);
                    }
                });
            }else{
                Glide.with(holder.itemView)
                        .load(ConnUtil.getPath()+indexServiceListBean.getRows().get(position).getImgUrl())
                        .into(holder.iv_serve_pic);
                holder.tv_serve_name.setText(indexServiceListBean.getRows().get(position).getServiceName());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getActivity(), "position:"+position, Toast.LENGTH_SHORT).show();
                        Log.e("ddd","position:"+position);
                        switch (list.get(position).getServiceName()){
                            case "找工作":{
                                navController.navigate(R.id.action_indexFrag_to_findWorkMainActivity);
                            }
                            case "生活缴费":{
                                navController.navigate(R.id.action_indexFrag_to_livePaymentMainActivity);
                            }
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return 10;
        }
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

    //初始化新闻分类
    public void initNewTab(){
        ApiServe apiServe = ConnUtil.creServe();
        Call<ResponseBody> categoryList = apiServe.getCategoryList();
        categoryList.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    Gson gson=new Gson();
                    NewSortBean newSortBean = gson.fromJson(string, NewSortBean.class);

                    TabHost tabhost = (TabHost) v.findViewById(android.R.id.tabhost);
                    tabhost.setup();
                    for(int i=0; i<newSortBean.getData().size(); i++){
                        tabhost.addTab(tabhost.newTabSpec(String.valueOf(i))
                                .setIndicator(newSortBean.getData().get(i).getName())
                                .setContent(IndexFrag.this));
                    }
                    initNewSortList(String.valueOf(newSortBean.getData().get(0).getId()));
                    tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                        @Override
                        public void onTabChanged(String tabId) {
                            initNewSortList(String.valueOf(newSortBean.getData().get(Integer.parseInt(tabId)).getId()));
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

    public void initNewSortList(String type){
        ApiServe initNewSortList = ConnUtil.creServe();
        Call<ResponseBody> pressList = initNewSortList.getPressList(type);
        pressList.enqueue(new Callback<ResponseBody>() {

            private NewSortListBean newSortListBean;

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    Gson gson=new Gson();
                    newSortListBean = gson.fromJson(string, NewSortListBean.class);

                    ListView new_sort_item = (ListView) v.findViewById(R.id.new_sort_item);
                    new_sort_item.setAdapter(new BaseAdapter() {
                        @Override
                        public int getCount() {
                            return newSortListBean.getRows().size();
                        }

                        @Override
                        public Object getItem(int position) {
                            return newSortListBean.getRows().get(position);
                        }

                        @Override
                        public long getItemId(int position) {
                            return position;
                        }

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {

                            View v = LayoutInflater.from(getActivity()).inflate(R.layout.layout_new_sort_item,null);
                            ImageView iv_new_sort_list_pic = (ImageView) v.findViewById(R.id.iv_new_sort_list_pic);
                            TextView tv_new_sort_list_title = (TextView) v.findViewById(R.id.tv_new_sort_list_title);
                            TextView tv_new_sort_list_content = (TextView) v.findViewById(R.id.tv_new_sort_list_content);
                            TextView tv_new_sort_list_comment = (TextView) v.findViewById(R.id.tv_new_sort_list_comment);
                            TextView tv_new_sort_list_public_date = (TextView) v.findViewById(R.id.tv_new_sort_list_public_date);

                            Glide.with(getActivity())
                                    .load(ConnUtil.getPath()+newSortListBean.getRows().get(position).getCover())
                                    .into(iv_new_sort_list_pic);
                            tv_new_sort_list_title.setText("标题："+newSortListBean.getRows().get(position).getTitle());
                            tv_new_sort_list_content.setText("内容："+newSortListBean.getRows().get(position).getContent());
                            tv_new_sort_list_comment.setText("评论总数："+newSortListBean.getRows().get(position).getCommentNum());
                            tv_new_sort_list_public_date.setText("发布事件"+newSortListBean.getRows().get(position).getPublishDate());
                            return v;
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

    @Override
    public View createTabContent(String tag) {
        View v=new View(getActivity());
        return v;
    }

    public void doSearch() {
        ApiServe apiServe = ConnUtil.creServe2();
        Call<NewSortListBean> pressListQuery = apiServe.getPressListQuery("1");
        pressListQuery.enqueue(new Callback<NewSortListBean>() {
            @Override
            public void onResponse(Call<NewSortListBean> call, Response<NewSortListBean> response) {
                lv_new_search.setAdapter(new BaseAdapter() {
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
                        View v = LayoutInflater.from(getActivity()).inflate(R.layout.layout_new_search_item, null);
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