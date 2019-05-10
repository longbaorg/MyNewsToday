package com.example.mynewstoday.fragment;



import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.AsyncTask;
import android.widget.ListView;

import com.example.mynewstoday.adapter.VideoAdapter;
import com.example.mynewstoday.entity.VideoBean;
import com.example.mynewstoday.utils.HttpUtilsVideo;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import com.example.mynewstoday.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {


    public String url =
            "http://baobab.kaiyanapp.com/api/v4/tabs/selected?udid=11111&vc=168&vn=3.3.1&deviceModel=Huawei6&first_channel=eyepetizer_baidu_market&last_channel=eyepetizer_baidu_market&system_version_code=20";
    public ListView videoLv;
    List<VideoBean.ItemListBean> mDatas = new ArrayList<>();
    private VideoAdapter adapter;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        videoLv =view.findViewById(R.id.video_lv);
//        设置数据源
//        设置适配器
        adapter = new VideoAdapter(view.getContext(),mDatas);
        videoLv.setAdapter(adapter);
//        加载网络数据
        loadWebData();


        return view;
    }

    //加载网络数据
    private void loadWebData() {
        new AsyncTask<Void,Void,String>(){
            protected String doInBackground(Void... voids) {
                String content = HttpUtilsVideo.getStringContent(url);
                return content;
            }

            protected void onPostExecute(String s) {
                if (s!=null&&!s.isEmpty()) {

//                    解析数据
                    Gson gson = new Gson();
                    VideoBean videoBean = gson.fromJson(s, VideoBean.class);
                    List<VideoBean.ItemListBean> itemList = videoBean.getItemList();
                    for (int i = 0; i < itemList.size(); i++) {
                        VideoBean.ItemListBean bean = itemList.get(i);
                        if (bean.getType().equals("video")) {
                            mDatas.add(bean);
                        }
                    }

                    adapter.notifyDataSetChanged();
                }
            }
        }.execute();
    }
}
