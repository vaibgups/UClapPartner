package your.service.com.partner.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import your.service.com.partner.fragment.OnGoingAllFragment;
import your.service.com.partner.fragment.OnGoingCallbackFragment;
import your.service.com.partner.fragment.OnGoingNumberSharedFragment;


public class PagerAdapter extends FragmentStatePagerAdapter
{
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                OnGoingAllFragment tab1 = new OnGoingAllFragment();
                return tab1;
            case 1:
                OnGoingNumberSharedFragment tab2 = new OnGoingNumberSharedFragment();
                return tab2;
            case 2:
                OnGoingCallbackFragment tab3 = new OnGoingCallbackFragment();
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
