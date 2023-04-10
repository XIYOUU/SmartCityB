package com.example.smartcityb.FindWork;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartcityb.FindWorkBean.DeliverReCordBean;
import com.example.smartcityb.MainActivity;
import com.example.smartcityb.Public.ApiServe;
import com.example.smartcityb.R;
import com.example.smartcityb.Utils.ConnUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DeliverReCordFragment extends Fragment {


    private RecyclerView rec_deliver_record;
    private DeliverReCordBean deliverReCordBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_deliver_re_cord, null, false);
        rec_deliver_record = (RecyclerView) v.findViewById(R.id.rec_deliver_record);


        initDeliverRecord();
        return v;
    }

    public void initDeliverRecord() {
        ApiServe apiServe = ConnUtil.creServe();
        Call<ResponseBody> deliverList = apiServe.getDeliverList(MainActivity.Token);
        deliverList.enqueue(new Callback<ResponseBody>() {


            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    Gson gson = new Gson();
                    deliverReCordBean = gson.fromJson(string, DeliverReCordBean.class);
                    rec_deliver_record.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rec_deliver_record.setAdapter(new DeliverAdapter());
                    rec_deliver_record.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

                } catch (IOException e) {


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }
    public class DeliverAdapter extends RecyclerView.Adapter<DeliverAdapter.DeliverHolder> {

        @NonNull
        @Override
        public DeliverHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.layout_find_house_deliver_record, null);
            return new DeliverHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull DeliverHolder holder, int position) {
            holder.tv_find_house_postName.setText("岗位名称:" + deliverReCordBean.getRows().get(position).getPostName());
            holder.tv_find_house_companyName.setText("公司名称:" + deliverReCordBean.getRows().get(position).getCompanyName());
            holder.tv_find_house_money.setText("薪资:" + deliverReCordBean.getRows().get(position).getMoney());
            holder.tv_find_house_satrTime.setText("投递时间:" + deliverReCordBean.getRows().get(position).getSatrTime());
        }

        @Override
        public int getItemCount() {
            return deliverReCordBean.getRows().size();
        }

        public class DeliverHolder extends RecyclerView.ViewHolder {

            private final TextView tv_find_house_postName;
            private final TextView tv_find_house_companyName;
            private final TextView tv_find_house_money;
            private final TextView tv_find_house_satrTime;

            public DeliverHolder(@NonNull View itemView) {
                super(itemView);
                tv_find_house_postName = (TextView) itemView.findViewById(R.id.tv_find_house_postName);
                tv_find_house_companyName = (TextView) itemView.findViewById(R.id.tv_find_house_companyName);
                tv_find_house_money = (TextView) itemView.findViewById(R.id.tv_find_house_money);
                tv_find_house_satrTime = (TextView) itemView.findViewById(R.id.tv_find_house_satrTime);

            }
        }
    }
}