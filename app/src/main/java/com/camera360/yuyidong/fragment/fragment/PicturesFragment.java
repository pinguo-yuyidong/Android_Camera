package com.camera360.yuyidong.fragment.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.camera360.yuyidong.fragment.R;
import com.camera360.yuyidong.fragment.adapter.GridViewAdapter;
import com.camera360.yuyidong.fragment.listener.GridViewItemClickListener;

/**
 * Created by yuyidong on 14-11-5.
 */
public class PicturesFragment extends Fragment{
    /**
     * gridview
     */
    private GridView mGridView;
    /**
     * 给adapter的inflater
     */
    private LayoutInflater mInflater;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.frag_allpictures,null);
        mGridView = (GridView) v.findViewById(R.id.grid_allpictures);
        mGridView.setAdapter(new GridViewAdapter(inflater,mGridView));
        mGridView.setOnItemClickListener(new GridViewItemClickListener());
        return v;

    }


}
