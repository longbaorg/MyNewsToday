package com.example.mynewstoday.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.mynewstoday.R;

public class InfoSencode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_sencode);

        TextView textView = findViewById(R.id.infotext);
        textView.setText("这是开发学习的项目，其中还有很多的不足和bug，以后有时间再继续完善。" +
                "本app利用了目前较好的框架来实现功能！" +
                "开发者QQ：1923439579" +
                "开发者邮箱：1923439579@qq.com");
    }
}
