package com.camera360.yuyidong.app2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by yuyidong on 14-11-4.
 */
public class Frag_one extends Fragment {

    private Button btn;
    private OnHeadlineSelectedListener mCallback;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("Frag_oneFrag_oneFrag_one");
        View v = inflater.inflate(R.layout.frag_one,null);
        btn = (Button) v.findViewById(R.id.btn_frag_1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(),"1111111111111",Toast.LENGTH_SHORT).show();
                mCallback.onArticleSelected(1);
            }
        });
        ListView lv_title = (ListView) getActivity().findViewById(R.id.lv_title);
        Infomation info = (Infomation) lv_title.getAdapter().getItem(0);
        System.out.println("info--->"+info.getText());
        return v;
    }

    public Frag_one() {
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        System.out.println("11111--------onAttach");
        // 这是为了保证Activity容器实现了用以回调的接口。如果没有，它会抛出一个异常。
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("11111--------onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        System.out.println("11111--------onResume");
        super.onResume();
    }

    @Override
    public void onStart() {
        System.out.println("11111--------onStart");
        btn.setVisibility(View.VISIBLE);
        super.onStart();
    }

    @Override
    public void onPause() {
        System.out.println("11111--------onPause");
        btn.setVisibility(View.INVISIBLE);
        super.onPause();
    }

    @Override
    public void onStop() {
        System.out.println("11111--------onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        System.out.println("11111--------onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        System.out.println("11111--------onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        System.out.println("11111--------onDetach");
        super.onDetach();
    }

    // 用来存放fragment的Activtiy必须实现这个接口
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

}
