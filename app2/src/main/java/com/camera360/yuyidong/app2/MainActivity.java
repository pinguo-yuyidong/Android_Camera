package com.camera360.yuyidong.app2;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements Frag_one.OnHeadlineSelectedListener {
    private ListView lv_title;
    private FrameLayout frameLayout;
    private List<Infomation> list;
    private LayoutInflater inflater;
    public List<Fragment> fragments;
    private Fragment fragOne;
    private Fragment fragTow;
    private Fragment fragThree;
    private android.support.v4.app.FragmentTransaction ft;
    private android.support.v4.app.FragmentManager fm;
    private int currentIndex=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_title = (ListView) findViewById(R.id.lv_title);
        frameLayout = (FrameLayout) findViewById(R.id.framelayout);

        list = new ArrayList<Infomation>();
        initInfomation();

        fragments = new ArrayList<Fragment>();
        initFragment();
        fm = getSupportFragmentManager();


        inflater = LayoutInflater.from(MainActivity.this);
        MyBaseAdapter adapter = new MyBaseAdapter(list,inflater);
        lv_title.setAdapter(adapter);
        lv_title.setOnItemClickListener(new MyItemClickListener());

    }

    private void initInfomation()
    {
        Infomation info1 = new Infomation("111");
        Infomation info2 = new Infomation("222");
        Infomation info3 = new Infomation("333");
        list.add(info1);
        list.add(info2);
        list.add(info3);
    }

    private void initFragment()
    {
        fragOne = new Frag_one();
        fragTow = new Frag_two();
        fragThree = new Frag_three();
        fragments.add(fragOne);
        fragments.add(fragTow);
        fragments.add(fragThree);
    }



    class MyItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            System.out.println("i---->" + i);
//            if(currentIndex==-1)
//                currentIndex=i;
//            else if(currentIndex != i) {
//                //fragments.get(currentIndex).onPause();
//                //currentIndex = i;
//            }
            Fragment fragment = fragments.get(i);

            if(!fragment.isAdded()) {
                if(currentIndex!=-1)
                    fragments.get(currentIndex).onPause();
                ft = fm.beginTransaction();
                ft.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.add(R.id.framelayout,fragment);
               // ft.addToBackStack(null);
                ft.commit();
                currentIndex=i;

            }
            else
            {
                ft = fm.beginTransaction();
                fragments.get(currentIndex).onPause();
                ft.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.hide(fragments.get(currentIndex));
                ft.show(fragments.get(i));
                fragments.get(i).onStart();
                ft.commit();
                currentIndex=i;

            }
//            Fragment fragment = fragments.get(i);
//            ft = fm.beginTransaction();
//            ft.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//            ft.replace(R.id.framelayout,fragment);
//            ft.addToBackStack(null);
//            ft.commit();

        }

    }

    @Override
    public void onArticleSelected(int position) {
        if(position == 1)
        {
            System.out.println("这个是再MainActivity中打印的!!!");
        }
    }



}
