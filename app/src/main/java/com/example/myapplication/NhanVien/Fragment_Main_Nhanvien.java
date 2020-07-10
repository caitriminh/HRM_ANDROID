package com.example.myapplication.NhanVien;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

public class Fragment_Main_Nhanvien extends Fragment {

    private TabLayout tabLayout;
    public static boolean is_chitiet;
    static ViewPager viewPager;
    Fragment_Them_Nhanvien fm_them_nv;
    Fragment_danhsach_nhanvien fm_ds_nv;
   //String[] tabTitle = {"CALLS", "CHAT", "CONTACTS"};
   // int[] unreadCount = {0, 5, 0};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_main_nhanvien, container, false);
        return rootView;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
        tabLayout = (TabLayout) getActivity().findViewById(R.id.tablayout);

        viewPager.setOffscreenPageLimit(2);

        getActivity().setTitle("NHAN VIEN");
        setUpViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public static void changePage(int position) {
        viewPager.setCurrentItem(position);
    }


    private void setUpViewPager(ViewPager viewPager) {
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
//        fm_them_nv = new Fragment_Them_Nhanvien();
//        fm_ds_nv = new Fragment_danhsach_nhanvien();
//
//        viewPagerAdapter.addFragment(fm_ds_nv, "DANH SACH");
//        viewPagerAdapter.addFragment(fm_them_nv, "THEM MOI");
//
//        viewPager.setAdapter(viewPagerAdapter);

    }
}
