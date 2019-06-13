package com.example.mynewstoday.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.mynewstoday.R;
import com.example.mynewstoday.adapter.MyFragmentAdapter;
import com.example.mynewstoday.entity.NetNews;
import com.example.mynewstoday.entity.NewsType;
import com.example.mynewstoday.utils.GlideImageLoader;
//import com.facebook.drawee.backends.pipeline.Fresco;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnBannerListener;


import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private List<NewsType> nt_list;
    private List<Fragment> mData;
    private ArrayList<String> listssurl;
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


//        Fresco.initialize(getContext());

//        for (String li : listssurl){
//            Log.d("TAG","--------listssurl------->"+li);
//        }
//        for (NewsType lid : nt_list){
//            Log.d("TAG","-------NewsType-------->titlt"+lid.getTitle());
//            Log.d("TAG","-------NewsType-------->url"+lid.getUrl());
//        }


//        NetNews news = new NetNews();
//        String url = news.getPicUrl();
//        String title = news.getTitle();
//        List<String> listssTitle= new ArrayList<>();
        //        listssTitle.add(title);
        listssurl= new ArrayList<>();
        listssurl.add("https://06imgmini.eastday.com/mobile/20190522/20190522202516_0666375f9d919ac8afcf03cd7e3bdc20_4_mwpm_03200403.jpg");
        listssurl.add("https://05imgmini.eastday.com/mobile/20190522/2019052220_c9107e16815b490dab25e76f93e0c895_3940_mwpm_03200403.jpg");
        listssurl.add("https://06imgmini.eastday.com/mobile/20190522/20190522202516_0666375f9d919ac8afcf03cd7e3bdc20_3_mwpm_03200403.jpg");
        listssurl.add("https://05imgmini.eastday.com/mobile/20190522/20190522201825_0554e0d366edaea025588db5cd8fc116_2_mwpm_03200403.jpg");
        listssurl.add("https://05imgmini.eastday.com/mobile/20190522/20190522201328_7da9464e6a012a766198491b7675608d_2_mwpm_03200403.jpg");
        listssurl.add("https://01imgmini.eastday.com/mobile/20190522/20190522194850_63c5b5fd0c303e585da77e9bca77c551_6_mwpm_03200403.jpg");
        listssurl.add("https://08imgmini.eastday.com/mobile/20190522/2019052219_7a8ef06ed4ae49fdb5a4f1bd44b08b62_0490_cover_mwpm_03200403.jpg");
        listssurl.add("https://03imgmini.eastday.com/mobile/20190522/2019052218_97f21ca78231436fbe50c7545b1e7b57_9119_mwpm_03200403.jpg");
        List<String> imageTitle = new ArrayList<>();
        imageTitle.add("一个军人给另一个人量衣服");
        imageTitle.add("一个非常时尚的女孩");
        imageTitle.add("时尚男装秀");
        imageTitle.add("漂亮的女人");
        imageTitle.add("两个美国人在游玩");
        imageTitle.add("加强军备");
        imageTitle.add("美国军官");
        imageTitle.add("伊拉克塔利班武装");
        Banner mBanner = view.findViewById(R.id.banner);
        mBanner.setImages(listssurl).setImageLoader(new GlideImageLoader()).start();
        //设置样式，里面有很多种样式可以自己都看看效果
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置轮播的动画效果,里面有很多种特效,可以都看看效果。
        mBanner.setBannerAnimation(Transformer.ZoomOutSlide);
        //轮播图片的文字
        mBanner.setBannerTitles(imageTitle);
        //设置轮播间隔时间
        mBanner.setDelayTime(5000);
        //设置是否为自动轮播，默认是true
        mBanner.isAutoPlay(true);
        //设置指示器的位置，小点点，居中显示
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(getContext() , "暂不可跳转，请点击下面新闻进行查看",Toast.LENGTH_LONG).show();
            }
        }).start();

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
        NewsType nt = new NewsType(1,"头条","https://v.juhe.cn/toutiao/index?type=top&key="+ APPKEY);
        nt_list.add(nt);

        nt = new NewsType(2,"社会","https://v.juhe.cn/toutiao/index?type=shehui&key="+ APPKEY);
        nt_list.add(nt);

        nt = new NewsType(3,"国内","https://v.juhe.cn/toutiao/index?type=guonei&key="+ APPKEY);
        nt_list.add(nt);

        nt = new NewsType(4,"国际","https://v.juhe.cn/toutiao/index?type=guoji&key="+ APPKEY);
        nt_list.add(nt);

        nt = new NewsType(5,"娱乐","https://v.juhe.cn/toutiao/index?type=yule&key="+ APPKEY);
        nt_list.add(nt);

        nt = new NewsType(6,"体育","https://v.juhe.cn/toutiao/index?type=tiyu&key="+ APPKEY);
        nt_list.add(nt);

        nt = new NewsType(7,"军事","https://v.juhe.cn/toutiao/index?type=junshi&key="+ APPKEY);
        nt_list.add(nt);

        nt = new NewsType(8,"科技","https://v.juhe.cn/toutiao/index?type=keji&key="+ APPKEY);
        nt_list.add(nt);

        nt = new NewsType(9,"财经","https://v.juhe.cn/toutiao/index?type=caijing&key="+ APPKEY);
        nt_list.add(nt);

        nt = new NewsType(10,"时尚","https://v.juhe.cn/toutiao/index?type=shishang&key="+ APPKEY);
        nt_list.add(nt);


    }


}
