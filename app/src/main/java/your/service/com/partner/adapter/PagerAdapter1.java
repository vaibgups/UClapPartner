package your.service.com.partner.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import your.service.com.partner.fragment.HiredAllFragment;
import your.service.com.partner.fragment.HiredReviewPendingFragment;
import your.service.com.partner.fragment.HiredReviewReceivedFragment;


public class PagerAdapter1 extends FragmentStatePagerAdapter
{
    int mNumOfTabs;

    public PagerAdapter1(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                HiredAllFragment tab1 = new HiredAllFragment();
                return tab1;
            case 1:
                HiredReviewPendingFragment tab2 = new HiredReviewPendingFragment();
                return tab2;
            case 2:
                HiredReviewReceivedFragment tab3 = new HiredReviewReceivedFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
