package com.example.whattodo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<Fragment>();
    List<String> titulosList = new ArrayList<String>();

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    public void addFragment(Fragment fragment, String titulo){
        fragmentList.add(fragment);
        titulosList.add(titulo);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return  titulosList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
