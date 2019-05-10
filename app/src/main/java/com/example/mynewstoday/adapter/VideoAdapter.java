package com.example.mynewstoday.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import com.example.mynewstoday.R;
import com.example.mynewstoday.entity.VideoBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends BaseAdapter {

//    视频碎片的适配器


    private Context context;
    private List<VideoBean.ItemListBean> mDatas;

    public VideoAdapter(Context context, List<VideoBean.ItemListBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        VideoViewHolder holder = null;
        if (convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_moive,null);
            holder = new VideoViewHolder();
            holder.videoPlayer = (JZVideoPlayerStandard) convertView.findViewById(R.id.item_video_jz);
            holder.summaryTv = (TextView) convertView.findViewById(R.id.item_video_tv);
            convertView.setTag(holder);
        }else{
            holder = (VideoViewHolder) convertView.getTag();
        }
        VideoBean.ItemListBean bean = mDatas.get(i);
        final VideoBean.ItemListBean.DataBean data = bean.getData();

        holder.summaryTv.setText(data.getDescription());
//        设置视频地址和标题的方法
        holder.videoPlayer.setUp(data.getPlayUrl(), JZVideoPlayer.SCREEN_WINDOW_LIST,data.getTitle());
        if (data.getTags().size()>0) {
            String bgUrl = data.getTags().get(0).getBgPicture();
//            设置显示的缩略图
            ImageView imageView = holder.videoPlayer.thumbImageView;
            Picasso.with(context).load(bgUrl).into(imageView);
        }

        holder.videoPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"正在播放了："+data.getTitle(),Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    class VideoViewHolder{
        JZVideoPlayerStandard videoPlayer;
        TextView summaryTv;
    }
}