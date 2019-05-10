package com.example.mynewstoday.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.mynewstoday.R;
import com.example.mynewstoday.adapter.MyFragmentAdapter;
import com.example.mynewstoday.entity.NewsType;


import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private List<NewsType> nt_list;
    private List<Fragment> mData;
    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TabLayout tabs = (TabLayout)view.findViewById(R.id.tabs);
        ViewPager vp = (ViewPager)view.findViewById(R.id.vp);
        initNewsType();
        initData();
        MyFragmentAdapter adapter = new MyFragmentAdapter(getFragmentManager(),mData,nt_list);
        vp.setAdapter(adapter);
        //把Tab和ViewPager关联到一起
        tabs.setupWithViewPager(vp);

        return view;
    }


    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < nt_list.size(); i++) {
            Fragment f = new NewsFragment();
            Bundle b = new Bundle();
            b.putSerializable("type",nt_list.get(i));
            f.setArguments(b);
            mData.add(f);
        }
    }

    private void initNewsType() {
        nt_list = new ArrayList<NewsType>();
        String APPKEY = "95bbd1bf1ced40d8e95c7e3288359714";
        NewsType nt = new NewsType(1,"头条","http://v.juhe.cn/toutiao/index?type=top&key="+ APPKEY);
        nt_list.add(nt);

        nt = new NewsType(2,"社会","http://v.juhe.cn/toutiao/index?type=shehui&key="+ APPKEY);
        nt_list.add(nt);

        nt = new NewsType(3,"国内","http://v.juhe.cn/toutiao/index?type=guonei&key="+ APPKEY);
        nt_list.add(nt);

        nt = new NewsType(4,"国际","http://v.juhe.cn/toutiao/index?type=guoji&key="+ APPKEY);
        nt_list.add(nt);

        nt = new NewsType(5,"娱乐","http://v.juhe.cn/toutiao/index?type=yule&key="+ APPKEY);
        nt_list.add(nt);

        nt = new NewsType(6,"体育","http://v.juhe.cn/toutiao/index?type=tiyu&key="+ APPKEY);
        nt_list.add(nt);

        nt = new NewsType(7,"军事","http://v.juhe.cn/toutiao/index?type=junshi&key="+ APPKEY);
        nt_list.add(nt);

        nt = new NewsType(8,"科技","http://v.juhe.cn/toutiao/index?type=keji&key="+ APPKEY);
        nt_list.add(nt);

        nt = new NewsType(9,"财经","http://v.juhe.cn/toutiao/index?type=caijing&key="+ APPKEY);
        nt_list.add(nt);

        nt = new NewsType(10,"时尚","http://v.juhe.cn/toutiao/index?type=shishang&key="+ APPKEY);
        nt_list.add(nt);

    }


}
