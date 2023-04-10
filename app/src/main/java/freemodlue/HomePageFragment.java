package freemodlue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.smartcityb.R;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;


import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment {
    Banner banner;
    List images = new ArrayList();
    List<appraise> data = new ArrayList<>();
    private LinearLayout fun1;
    private LinearLayout fun2;
    private LinearLayout fun3;
    private LinearLayout fun4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.freemoule_fargment,null,false);

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
        data.add(new appraise("夕阳红养老院1","",R.mipmap.ic_launcher_round,"去预约->"));
        data.add(new appraise("夕阳红养老院2","",R.mipmap.ic_launcher_round,"去预约->"));
        data.add(new appraise("夕阳红养老院3","",R.mipmap.ic_launcher_round,"去预约->"));
        data.add(new appraise("夕阳红养老院4","",R.mipmap.ic_launcher_round,"去预约->"));
        data.add(new appraise("夕阳红养老院5","",R.mipmap.ic_launcher_round,"去预约->"));
        data.add(new appraise("夕阳红养老院6","",R.mipmap.ic_launcher_round,"去预约->"));
        data.add(new appraise("夕阳红养老院7","",R.mipmap.ic_launcher_round,"去预约->"));

        ListView listView = view.findViewById(R.id.free_listview);
        listView.setAdapter(new myAdapter(data));

        banner = view.findViewById(R.id.free_banner);
        fun1 = view.findViewById(R.id.fun1);
        fun2 = view.findViewById(R.id.fun2);
        fun3 = view.findViewById(R.id.fun3);
        fun4 = view.findViewById(R.id.fun4);

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                Intent intent = new Intent(getActivity(),Info.class);
                startActivity(intent);
            }

        });

        fun1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PinguActivity.class);
                startActivity(intent);
            }
        });

        fun2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),HuanJIng.class);
                startActivity(intent);
            }
        });

        fun4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),JianKangActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"预约成功", Toast.LENGTH_SHORT).show();
            }
        });

        images.add(R.mipmap.banner1);
        images.add(R.mipmap.banner2);
        images.add(R.mipmap.banner3);
        images.add(R.mipmap.banner4);
        images.add(R.mipmap.banner5);
//        banner.setImages(images);
//        banner.setImageLoader(new MyImageLoader());
        banner.start();

        return view;
    }

    static void bannerFunction(View view, int i){

    }

//    static class MyImageLoader extends ImageLoader{
//        @Override
//        public void displayImage(Context context, Object path, ImageView imageView) {
//            imageView.setImageResource((int)path);
//        }
//    }
}
