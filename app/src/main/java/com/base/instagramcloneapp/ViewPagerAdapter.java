package com.base.instagramcloneapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {



    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new Users_tab();
            case 1: return new Profile_tab();
            case 2: return new SharePicture_tab();
            default: return  null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
