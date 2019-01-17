package your.service.com.partner.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import your.service.com.partner.R;
import your.service.com.partner.adapter.PagerAdapter1;


/**
 * A simple {@link Fragment} subclass.
 */
public class HiredFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public HiredFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hired, container, false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText(""+getResources().getString(R.string.hired_tabItem1)));
        tabLayout.addTab(tabLayout.newTab().setText(""+getResources().getString(R.string.hired_tabItem2)));
        tabLayout.addTab(tabLayout.newTab().setText(""+getResources().getString(R.string.hired_tabItem3)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        final PagerAdapter1 adapter1 = new PagerAdapter1
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter1);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }
}
//        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
//        addTabs(viewPager);
//
//        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
//        tabLayout.setupWithViewPager(viewPager);
//
//        return view;
//    }
//
//    private void addTabs(ViewPager viewPager) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
//        adapter.addFrag(new HiredAllFragment(), ""+getResources().getString(R.string.hired_tabItem1));
//        adapter.addFrag(new HiredReviewPendingFragment(), ""+getResources().getString(R.string.hired_tabItem2));
//        adapter.addFrag(new HiredReviewReceivedFragment(), ""+getResources().getString(R.string.hired_tabItem3));
//        viewPager.setAdapter(adapter);
//    }
//
//    //    // Adapter for the viewpager using FragmentPagerAdapter
//    static class ViewPagerAdapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();
//
//        public ViewPagerAdapter(FragmentManager manager) {
//            super(manager);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragmentList.size();
//        }
//
//        public void addFrag(Fragment fragment, String title) {
//            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitleList.get(position);
//        }
//    }
//
//}
