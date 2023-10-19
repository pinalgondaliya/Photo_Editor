package com.example.photoeditor.Adapter;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.example.photoeditor.Fragment.SpiralFragment;
import com.example.photoeditor.ModelClass.MainModel;

import java.util.ArrayList;

/* loaded from: classes5.dex */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<MainModel.Datum> category;
    private int mNumOfTabs;

    public ViewPagerAdapter(FragmentManager supportFragmentManager, int NumOfTabs, ArrayList<MainModel.Datum> category) {
        super(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mNumOfTabs = NumOfTabs;
        this.category = category;
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter
    public Fragment getItem(int position) {
        String c_name = this.category.get(position).category_name;
        Bundle b = new Bundle();
        b.putInt("position", position);
        b.putSerializable("arraylist", this.category.get(position).cat_array);
        b.putString("category", c_name);
        Fragment frag = new SpiralFragment();
        frag.setArguments(b);
        return frag;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.mNumOfTabs;
    }
}
