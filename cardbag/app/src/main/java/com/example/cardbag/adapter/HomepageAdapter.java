package com.example.cardbag.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cardbag.HomepageActivity;
import com.example.cardbag.bean.HomepageBean;

import java.util.List;

import com.example.cardbag.R;
import com.example.cardbag.bean.HomepageBean;

public class HomepageAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<HomepageBean> list;
    public HomepageAdapter(Context context, List<HomepageBean> list){
        this.layoutInflater=LayoutInflater.from(context);
        this.list=list;
    }



    @Override
    public int getCount() {
        return list==null ? 0 : list.size();
    }
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.homepage_item_layout,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        HomepageBean noteInfo=(HomepageBean) getItem(position);
        viewHolder.tvHomepageName.setText(noteInfo.getHomepageName());
        viewHolder.tvHomepageTime.setText(noteInfo.getHomepageTime());
        return convertView;
    }
    class ViewHolder{
        TextView tvHomepageName;
        TextView tvHomepageTime;
        public ViewHolder(View view){
            tvHomepageName=(TextView) view.findViewById(R.id.item_content);
            tvHomepageTime=(TextView) view.findViewById(R.id.item_time);
        }
    }
}
