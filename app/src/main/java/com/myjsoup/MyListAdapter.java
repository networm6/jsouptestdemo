package com.myjsoup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import com.bumptech.glide.Glide;
import android.app.Activity;


public class MyListAdapter extends BaseAdapter {

    private List<Bean> DataList;
    private LayoutInflater inflater;
    private Activity con;
    public  MyListAdapter (List<Bean> mDataList, Activity context) {
		this.DataList = mDataList;
		this.inflater = LayoutInflater.from(context);
		con=context;
    }
    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public Object getItem(int position) {
        return DataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载布局为一个视图
        View view = inflater.inflate(R.layout.item,null);
        Bean mData = (Bean) getItem(position);

        //在view 视图中查找 组件
        TextView tv_one = (TextView) view.findViewById(R.id.itemTextView1);//作者，阅读，简介，书名
        TextView tv_two = (TextView) view.findViewById(R.id.itemTextView2);
		TextView tv_three = (TextView) view.findViewById(R.id.itemTextView3);
		TextView tv_four = (TextView) view.findViewById(R.id.itemTextView4);
		
		ImageView img=view.findViewById(R.id.itemImageView1);//图片
        //为Item 里面的组件设置相应的数据
        tv_one.setText(mData.get作者());
		tv_two.setText(mData.get阅读());
        tv_three.setText(mData.get简介());
		tv_four.setText(mData.get书名());
        Glide.with(con).load(mData.get图片()).into(img);
        return view;
    }
}



