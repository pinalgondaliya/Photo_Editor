package com.example.photoeditor.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.photoeditor.Fragment.FirstFragment;
import com.example.photoeditor.Fragment.FiveFragment;
import com.example.photoeditor.Fragment.FourFragment;
import com.example.photoeditor.Fragment.SecondFragment;
import com.example.photoeditor.Fragment.SixFragment;
import com.example.photoeditor.Fragment.ThreeFragment;

/* loaded from: classes5.dex */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 6;

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FirstFragment();
            case 1:
                return new SecondFragment();
            case 2:
                return new ThreeFragment();
            case 3:
                return new FourFragment();
            case 4:
                return new FiveFragment();
            case 5:
                return new SixFragment();
            default:
                return new FirstFragment();
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }
}
