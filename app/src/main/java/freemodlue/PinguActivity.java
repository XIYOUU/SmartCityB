package freemodlue;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcityb.R;


public class PinguActivity extends AppCompatActivity {

    private RadioGroup huodong;
    private CheckBox zhengchang1;
//    private CheckBox zhengchang2;
    private CheckBox chidai;
    private CheckBox yiyu;
    private CheckBox baoli;
    private CheckBox yinshi;
    private CheckBox xizao;
    private CheckBox chuanyi;
    private CheckBox peishi;
    private TextView feiyong;
    private TextView fuwu;
    public int money = 0;
    public int clickTimes = 0;
    public String peizhi = "护士24h服务";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pingu);

        zhengchang1 = findViewById(R.id.zhengchang1);
//        zhengchang2 = findViewById(R.id.zhenchang2);
        chidai = findViewById(R.id.chidai);
        yiyu = findViewById(R.id.yiyu);
        baoli = findViewById(R.id.baoli);
        yinshi = findViewById(R.id.yinshi);
        xizao = findViewById(R.id.xizhao);
        chuanyi = findViewById(R.id.chuanyi);
        peishi = findViewById(R.id.xiushi);
        huodong = findViewById(R.id.huodong);
        fuwu = findViewById(R.id.fuwu);
        feiyong = findViewById(R.id.feiyong);

        zhengchang1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(chidai.isChecked()){
                        money -= 200;
                        feiyong.setText(money+"");
                    }
                    if(yiyu.isChecked()){
                        money -= 200;
                        feiyong.setText(money+"");
                    }
                    if(baoli.isChecked()){
                        money -= 200;
                        feiyong.setText(money+"");
                    }
                    chidai.setChecked(false);
                    yiyu.setChecked(false);
                    baoli.setChecked(false);
                }
            }
        });

        MyOnCheckedChangeListener myOnCheckedChangeListener = new MyOnCheckedChangeListener();
        chidai.setOnCheckedChangeListener(myOnCheckedChangeListener);
        yiyu.setOnCheckedChangeListener(myOnCheckedChangeListener);
        baoli.setOnCheckedChangeListener(myOnCheckedChangeListener);

        huodong.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(clickTimes != 0 && checkedId == 0){
                    money -= 200;
                    clickTimes = 0;
                    feiyong.setText(money+"");
                }else if(checkedId != 0 && clickTimes == 0){
                    money += 200;
                    feiyong.setText(money+"");
                }
            }
        });

        fuwu.setText(peizhi);
    }
        class MyOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                money += 200;
                feiyong.setText(money+"");
            }else{
                money -= 200;
                feiyong.setText(money+"");
            }
        }
    }
}
