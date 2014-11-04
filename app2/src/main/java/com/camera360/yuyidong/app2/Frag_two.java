package com.camera360.yuyidong.app2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by yuyidong on 14-11-4.
 */
public class Frag_two extends Fragment {
    private int num=222;
    private Button btn;
    public int getNum() {
        return num;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("Frag_twoFrag_twoFrag_two");
        View v = inflater.inflate(R.layout.frag_two,null);
        btn = (Button) v.findViewById(R.id.btn_frag_2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "2222222222222222222222", Toast.LENGTH_SHORT).show();
            }
        });
        ListView lv_title = (ListView) getActivity().findViewById(R.id.lv_title);
        Infomation info = (Infomation) lv_title.getAdapter().getItem(2);
        System.out.println("info--->"+info.getText());
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main,menu);
    }
    @Override
    public void onAttach(Activity activity) {
        System.out.println("22222--------onAttach");
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("22222--------onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        System.out.println("22222--------onResume");
        super.onResume();
    }

    @Override
    public void onStart() {
        System.out.println("22222--------onStart");
        btn.setVisibility(View.VISIBLE);
        super.onStart();
    }

    @Override
    public void onPause() {
        System.out.println("22222--------onPause");
        btn.setVisibility(View.INVISIBLE);
        super.onPause();
    }

    @Override
    public void onStop() {
        System.out.println("22222--------onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        System.out.println("22222--------onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        System.out.println("22222--------onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        System.out.println("22222--------onDetach");
        super.onDetach();
    }
}
