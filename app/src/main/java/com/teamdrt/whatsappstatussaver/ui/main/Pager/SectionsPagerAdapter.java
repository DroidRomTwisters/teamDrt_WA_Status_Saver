package com.teamdrt.whatsappstatussaver.ui.main.Pager;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.teamdrt.whatsappstatussaver.R;
import com.teamdrt.whatsappstatussaver.ui.main.Downloads.DownloadsFragment;
import com.teamdrt.whatsappstatussaver.ui.main.Image.ImageFragment;
import com.teamdrt.whatsappstatussaver.ui.main.Video.VideoFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super ( fm ,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return VideoFragment.newInstance ();
            case 2:
                return DownloadsFragment.newInstance ();
            default:
                return ImageFragment.newInstance ();
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources ().getString ( TAB_TITLES[ position ] );
    }

    @Override
    public int getCount() {
        return 3;
    }
}