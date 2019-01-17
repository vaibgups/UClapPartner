package your.service.com.partner.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import your.service.com.partner.R;
import your.service.com.partner.activities.AccountSettingActivity;
import your.service.com.partner.activities.ContactUsActivity;
import your.service.com.partner.activities.EarnFreeCreditActivity;
import your.service.com.partner.activities.FindFriendsActivity;
import your.service.com.partner.activities.GSTDetailsActivity;
import your.service.com.partner.activities.HowItWorkActivity;
import your.service.com.partner.activities.TermsOfUseActivity;
import your.service.com.partner.adapter.CustomAdapter;
import your.service.com.partner.adapter.ListCustomAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {


    ListView simpleList;
   // String countryList[] = {"GST Details", "Find Friends on UrbanClap", "Earn Free Credits", "Account settings"};
   String countryList[] = {"Account settings"};
    int flags[] = {R.drawable.ic_keyboard_arrow_right_black_24dp, R.drawable.ic_keyboard_arrow_right_black_24dp, R.drawable.ic_keyboard_arrow_right_black_24dp, R.drawable.ic_keyboard_arrow_right_black_24dp};

    ListView simpleList1;
    String countryList1[] = {"How it works", "Terms of Use", "Contact Us", "Rate App on Playstore", "Download UrbanClap Customer App"};
    int flags1[] = {R.drawable.ic_keyboard_arrow_right_black_24dp, R.drawable.ic_keyboard_arrow_right_black_24dp, R.drawable.ic_keyboard_arrow_right_black_24dp, R.drawable.ic_keyboard_arrow_right_black_24dp, R.drawable.ic_keyboard_arrow_right_black_24dp};

    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_more, container, false);

        RelativeLayout rl = view.findViewById(R.id.item1);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder  = new AlertDialog.Builder(getContext());
                builder.setMessage(getResources().getString(R.string.alertbox_message));
                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        simpleList = view.findViewById(R.id.listView1);
        simpleList.setScrollContainer(false);
        CustomAdapter customAdapter = new CustomAdapter(getContext(), countryList, flags);
        simpleList.setAdapter(customAdapter);

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0)
                {
                    Intent intent = new Intent(getActivity(), AccountSettingActivity.class);
                    startActivity(intent);
//                    Intent intent = new Intent(getActivity(), GSTDetailsActivity.class);
//                    startActivity(intent);
                }
//                else if (position == 1)
//                {
//                    Intent intent = new Intent(getActivity(), FindFriendsActivity.class);
//                    startActivity(intent);
//                    Toast.makeText(getContext(), "position2", Toast.LENGTH_SHORT).show();
//                }
//                else if (position == 2)
//                {
//                    Intent intent = new Intent(getActivity(), EarnFreeCreditActivity.class);
//                    startActivity(intent);
//                }
//                else if (position == 3)
//                {
//                    Intent intent = new Intent(getActivity(), AccountSettingActivity.class);
//                    startActivity(intent);
//                }
            }
        });
        simpleList1 = view.findViewById(R.id.listView2);
        simpleList1.setScrollContainer(false);
        ListCustomAdapter listCustomAdapter = new ListCustomAdapter(getContext(),countryList1,flags1);
        simpleList1.setAdapter(listCustomAdapter);

        simpleList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0)
                {
                    Intent intent = new Intent(getActivity(), HowItWorkActivity.class);
                    startActivity(intent);
                }
                else if (position == 1)
                {
                    Intent intent = new Intent(getActivity(), TermsOfUseActivity.class);
                    startActivity(intent);
                }
                else if (position == 2)
                {
                    Intent intent = new Intent(getActivity(), ContactUsActivity.class);
                    startActivity(intent);
                }
                else if (position == 3)
                {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.urbanclap.provider "));
                    startActivity(i);
                }
                else if (position == 4)
                {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.urbanclap.urbanclap"));
                    startActivity(i);

                }
            }
        });
        return view;
    }

}
