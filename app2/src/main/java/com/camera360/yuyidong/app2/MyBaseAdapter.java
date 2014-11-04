package com.camera360.yuyidong.app2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yuyidong on 14-11-4.
 */
public class MyBaseAdapter extends BaseAdapter {

    private List<Infomation> list;
    private LayoutInflater inflater;
    public MyBaseAdapter(List<Infomation> list, LayoutInflater inflater) {
        this.list = list;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.item_title,null);
        TextView textView = (TextView) v.findViewById(R.id.item_text);
        textView.setText(list.get(i).getText());
        return v;
    }
}
