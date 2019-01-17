package your.service.com.partner.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import your.service.com.partner.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class OnGoingAllFragment extends Fragment {


    public OnGoingAllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_going_all, container, false);
    }

}
