package com.example.quizmaster.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quizmaster.ui.fragment.HomeFragment;
import com.example.quizmaster.ui.fragment.PracticeFragment;
import com.example.quizmaster.ui.fragment.FavoriteFragment;
import com.example.quizmaster.ui.fragment.WrongFragment;
import com.example.quizmaster.ui.fragment.ProfileFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new PracticeFragment();
            case 2:
                return new FavoriteFragment();
            case 3:
                return new WrongFragment();
            case 4:
                return new ProfileFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}