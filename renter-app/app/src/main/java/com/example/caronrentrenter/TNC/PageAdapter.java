package com.example.caronrentrenter.TNC;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PageAdapter extends FragmentStateAdapter {
    public PageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public Fragment createFragment(int position){
        switch (position){
            case 0:
                return new english();
            case 1:
                return new gujarati();
            case 2:
                return new hindi();
            case 3:
                return new tamil();
            case 4:
                return new marathi();
            default:
                return new english();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
//public class PageAdapter extends FragmentPagerAdapter {
//    int numcount;
//    public PageAdapter(@NonNull FragmentManager fm ,int numcount) {
//        super(fm);
//        this.numcount = numcount;
//    }
//
//    @NonNull
//    @Override
//    public Fragment getItem(int position) {
//        switch(position){
//            case 0:
//                english english = new english();
//                return english;
//            case 1:
//                gujarati gujarati = new gujarati();
//                return gujarati;
//            case 2:
//                hindi hindi = new hindi();
//                return hindi;
//            case 3:
//                marathi marathi = new marathi();
//                return marathi;
//
//            case 4:
//                tamil tamil = new tamil();
//                return tamil;
//
//
//
//        }
//
//        return null;

//    @Override
//    public int getCount() {
//        return numcount;
//    }
