package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;



import java.util.List;

public class MainActivity2 extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        Fragment fragment = new Fragment_Main_Nhanvien();
//        open_fragment(fragment);
    }

    public void open_fragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        List<Fragment> arr_fragment = getSupportFragmentManager().getFragments();
        if (arr_fragment != null) {
            for (Fragment fm : arr_fragment) {
                if (fm != null) {
                    ft.remove(fm);
                }
            }
        }
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

    }


}