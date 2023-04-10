package freemodlue;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.smartcityb.R;

import java.util.ArrayList;
import java.util.List;

public class Info extends AppCompatActivity {

    List<appraise> appraise = new ArrayList<>();

    private ListView pingjia;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yanglao_info);

        class myAdapter extends BaseAdapter {
            List<appraise> data;
            public myAdapter(List<appraise> data){
                this.data = data;
            };
            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = getLayoutInflater().inflate(R.layout.listview_item,null,false);
                ImageView avatar = v.findViewById(R.id.avatar);
                TextView name = v.findViewById(R.id.name);
                TextView content = v.findViewById(R.id.content);
                TextView time = v.findViewById(R.id.time);
                appraise a = data.get(position);
                avatar.setImageResource(a.getAvatar());
                name.setText(a.getName());
                content.setText(a.getContent());
                time.setText(a.getTime());

                return v;
            }
        }

        appraise.add(new appraise("用户136***6522","好，很满意",R.mipmap.mylogo,"2021-12-27"));
        appraise.add(new appraise("用户136***6522","好，很满意",R.mipmap.mylogo,"2021-12-27"));
        appraise.add(new appraise("用户136***6522","好，很满意",R.mipmap.mylogo,"2021-12-27"));
        appraise.add(new appraise("用户136***6522","好，很满意",R.mipmap.mylogo,"2021-12-27"));
        appraise.add(new appraise("用户136***6522","好，很满意",R.mipmap.mylogo,"2021-12-27"));
        appraise.add(new appraise("用户136***6522","好，很满意",R.mipmap.mylogo,"2021-12-27"));
        appraise.add(new appraise("用户136***6522","好，很满意",R.mipmap.mylogo,"2021-12-27"));
        appraise.add(new appraise("用户136***6522","好，很满意",R.mipmap.mylogo,"2021-12-27"));
        appraise.add(new appraise("用户136***6522","好，很满意",R.mipmap.mylogo,"2021-12-27"));
        appraise.add(new appraise("用户136***6522","好，很满意",R.mipmap.mylogo,"2021-12-27"));
        appraise.add(new appraise("用户136***6522","好，很满意",R.mipmap.mylogo,"2021-12-27"));
        appraise.add(new appraise("用户136***6522","好，很满意",R.mipmap.mylogo,"2021-12-27"));
        appraise.add(new appraise("用户136***6522","好，很满意",R.mipmap.mylogo,"2021-12-27"));

        pingjia = findViewById(R.id.info_listview);
        pingjia.setAdapter(new myAdapter(appraise));
    }
}
