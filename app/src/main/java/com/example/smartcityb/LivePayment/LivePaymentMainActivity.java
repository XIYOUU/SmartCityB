package com.example.smartcityb.LivePayment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smartcityb.Public.ApiServe;
import com.example.smartcityb.R;
import com.example.smartcityb.Utils.ConnUtil;
import com.example.smartcityb.livePaymentBean.LivingCategoryBean;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LivePaymentMainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private LivingCategoryBean livingCategoryBeans;
    private ImageView quit;
    private Button btn_manege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_payment_main);
        quit = (ImageView) findViewById(R.id.quit);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        btn_manege = (Button) findViewById(R.id.btn_manege);
        btn_manege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LivePaymentMainActivity.this,ManegeActivity.class);
                startActivity(intent);
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initServe();
    }

    public void initServe(){
        ApiServe apiServe = ConnUtil.creServe2();
        Call<LivingCategoryBean> livingCategory = apiServe.getLivingCategory();
        livingCategory.enqueue(new Callback<LivingCategoryBean>() {
            @Override
            public void onResponse(Call<LivingCategoryBean> call, Response<LivingCategoryBean> response) {

                livingCategoryBeans=response.body();
                recycler.setLayoutManager(new GridLayoutManager(LivePaymentMainActivity.this,2));
                recycler.setAdapter(new liveAdapter());
            }

            @Override
            public void onFailure(Call<LivingCategoryBean> call, Throwable throwable) {

            }
        });

    }
    public class liveAdapter extends RecyclerView.Adapter<liveAdapter.liveHolder>{
        @NonNull
        @Override
        public liveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new liveHolder(LayoutInflater.from(LivePaymentMainActivity.this).inflate(R.layout.layout_live_catecory_item, null));
        }

        @Override
        public void onBindViewHolder(@NonNull liveHolder holder, int position) {
            Glide.with(LivePaymentMainActivity.this)
                    .load(ConnUtil.getPath()+livingCategoryBeans.getData().get(position).getImgUrl())
                    .into(holder.image);
            holder.name.setText(livingCategoryBeans.getData().get(position).getLiveName());
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (livingCategoryBeans.getData().get(position).getLiveName()){
                        case "水费":{
                            Intent intent=new Intent(LivePaymentMainActivity.this,WaterFreeActivity.class);
                            startActivity(intent);
                            break;
                        }
                        case "电费":{
                            Intent intent=new Intent(LivePaymentMainActivity.this,EleFreeActivity.class);
                            startActivity(intent);
                            break;
                        }
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return livingCategoryBeans.getData().size();
        }

        public class liveHolder extends RecyclerView.ViewHolder {

            private final ImageView image;
            private final TextView name;
            private final LinearLayout ll;

            public liveHolder(@NonNull View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.image);
                name = (TextView) itemView.findViewById(R.id.name);
                ll = (LinearLayout) itemView.findViewById(R.id.ll);

            }
        }
    }
}