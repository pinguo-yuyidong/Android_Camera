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
public class Frag_three extends Fragment {
    private Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("Frag_threeFrag_threeFrag_three");
        View v = inflater.inflate(R.layout.frag_three,null);
        btn = (Button) v.findViewById(R.id.btn_frag_3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "33333333333333", Toast.LENGTH_SHORT).show();
            }
        });
        ListView lv_title = (ListView) getActivity().findViewById(R.id.lv_title);
        Infomation info = (Infomation) lv_title.getAdapter().getItem(1);
        System.out.println("info--->"+info.getText());

        return v;
    }
    @Override
    public void onAttach(Activity activity) {
        System.out.println("33333--------onAttach");
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("33333--------onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        System.out.println("33333--------onResume");
        super.onResume();
    }

    @Override
    public void onStart() {
        System.out.println("33333--------onStart");
        btn.setVisibility(View.VISIBLE);
        super.onStart();
    }

    @Override
    public void onPause() {
        System.out.println("33333--------onPause");
        btn.setVisibility(View.INVISIBLE);
        super.onPause();
    }

    @Override
    public void onStop() {
        System.out.println("33333--------onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        System.out.println("33333--------onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        System.out.println("33333--------onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        System.out.println("33333--------onDetach");
        super.onDetach();
    }
}
