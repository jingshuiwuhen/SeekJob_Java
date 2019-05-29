package com.example.liu.seekjob.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liu.seekjob.R;
import com.example.liu.seekjob.beans.JobBean;
import com.example.liu.seekjob.utils.Constants;
import com.example.liu.seekjob.utils.Util;

import java.util.List;

public class AllJobsListViewAdapter extends BaseAdapter {
    private List<JobBean> mList;
    private Context mContext;

    public AllJobsListViewAdapter(Context context, List<JobBean> list) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.all_jobs_list_view_item, parent, false);
            viewHolder.mHeader = convertView.findViewById(R.id.iv_header_icon);
            viewHolder.mHits = convertView.findViewById(R.id.tv_hits);
            viewHolder.mFlagBg = convertView.findViewById(R.id.triangle_flag_bg);
            viewHolder.mFlagText = convertView.findViewById(R.id.tv_triangle_flag_text);
            viewHolder.mTitle = convertView.findViewById(R.id.tv_title);
            viewHolder.mSkill = convertView.findViewById(R.id.tv_skill);
            viewHolder.mPublisher = convertView.findViewById(R.id.tv_publisher);
            viewHolder.mPublishTime = convertView.findViewById(R.id.tv_publish_time);
            convertView.setTag(viewHolder);
        }

        Util.loadImg(mContext, mList.get(position).getPublisherHeadImg(), viewHolder.mHeader);
        viewHolder.mHits.setText(String.valueOf(mList.get(position).getHits()));
        setFlag(position, viewHolder.mFlagBg, viewHolder.mFlagText);
        viewHolder.mTitle.setText(mList.get(position).getTitle());
        viewHolder.mSkill.setText(mList.get(position).getSkill());
        viewHolder.mPublisher.setText(mList.get(position).getPublisher());
        setPublishTime(position, viewHolder.mPublishTime);
        return convertView;
    }

    private void setFlag(int position, View flagBg, TextView flagText) {
        switch (mList.get(position).getState()) {
            case Constants.ALL_JOBS_LIST_ITEM_STATE_NEW:
                flagBg.setVisibility(View.VISIBLE);
                flagBg.setBackground(mContext.getResources().getDrawable(R.drawable.right_top_triangle_shape_red, null));
                flagText.setVisibility(View.VISIBLE);
                flagText.setText(R.string.all_jobs_fragment_new_job);
                break;
            case Constants.ALL_JOBS_LIST_ITEM_STATE_READ:
                flagBg.setVisibility(View.VISIBLE);
                flagBg.setBackground(mContext.getResources().getDrawable(R.drawable.right_top_triangle_shape_gray, null));
                flagText.setVisibility(View.VISIBLE);
                flagText.setText(R.string.all_jobs_fragment_read);
                break;
            case Constants.ALL_JOBS_LIST_ITEM_STATE_COLLECT:
                flagBg.setVisibility(View.VISIBLE);
                flagBg.setBackground(mContext.getResources().getDrawable(R.drawable.right_top_triangle_shape_green, null));
                flagText.setVisibility(View.VISIBLE);
                flagText.setText(R.string.all_jobs_fragment_collect);
                break;
            default:
                flagBg.setVisibility(View.GONE);
                flagText.setVisibility(View.GONE);
                break;
        }
    }

    private void setPublishTime(int position, TextView publishTime) {
        int time = (int) (System.currentTimeMillis() - mList.get(position).getPublishTime()) / 1000;
        String showText;
        if (time >= 0 && time < 60) {
            showText = mContext.getResources().getString(R.string.all_jobs_fragment_just_now);
        } else if (time >= 60 && time < 60 * 60){
            showText = String.format(mContext.getResources().getString(R.string.all_jobs_fragment_before_minute), String.valueOf(time / 60));
        } else if (time >= 60 * 60 && time < 60 * 60 * 24){
            showText = String.format(mContext.getResources().getString(R.string.all_jobs_fragment_before_hour), String.valueOf(time / (60 * 60)));
        } else if (time >= 60 * 60 * 24 && time < 60 * 60 * 24 * 10){
            showText = String.format(mContext.getResources().getString(R.string.all_jobs_fragment_before_day), String.valueOf(time / (60 * 60 * 24)));
        } else {
            showText = String.format(mContext.getResources().getString(R.string.all_jobs_fragment_before_day), Util.formatDate(mList.get(position).getPublishTime(), "yyyy-MM-dd"));
        }
        publishTime.setText(showText);
    }

    private class ViewHolder {
        private ImageView mHeader;
        private TextView mHits;
        private View mFlagBg;
        private TextView mFlagText;
        private TextView mTitle;
        private TextView mSkill;
        private TextView mPublisher;
        private TextView mPublishTime;
    }
}
