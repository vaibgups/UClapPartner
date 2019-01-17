package your.service.com.partner.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import your.service.com.partner.R;
import your.service.com.partner.activities.AboutMeActivity;
import your.service.com.partner.activities.AddServiceActivity;
import your.service.com.partner.activities.AwardCertificateActivity;
import your.service.com.partner.activities.IdentityVerificationActivity;
import your.service.com.partner.activities.SubmitReferenceActivity;
import your.service.com.partner.activities.UploadPhotoActivity;

public class ProfileFragment extends Fragment implements View.OnClickListener
{

    LinearLayout identity_verify,about_me,upload_photo,award_photo,add_service,submit_reference;
    Button submit_approval;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        identity_verify = view.findViewById(R.id.identity);
        about_me = view.findViewById(R.id.about);
        upload_photo = view.findViewById(R.id.upload);
        award_photo = view.findViewById(R.id.award);
        add_service = view.findViewById(R.id.service);
        submit_reference = view.findViewById(R.id.submit);

        submit_approval = view.findViewById(R.id.submit_approval);

        identity_verify.setOnClickListener(this);
        about_me.setOnClickListener(this);
        upload_photo.setOnClickListener(this);
        award_photo.setOnClickListener(this);
        add_service.setOnClickListener(this);
        submit_reference.setOnClickListener(this);
        submit_approval.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();

        switch (id)
        {
            case R.id.identity:
            {
                Intent intent = new Intent(getActivity(), IdentityVerificationActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.about:
            {
                Intent intent = new Intent(getActivity(), AboutMeActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.upload:
            {
                Intent intent = new Intent(getActivity(), UploadPhotoActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.award:
            {
                Intent intent = new Intent(getActivity(), AwardCertificateActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.service:
            {
                Intent intent = new Intent(getActivity(), AddServiceActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.submit:
            {
                Intent intent = new Intent(getActivity(), SubmitReferenceActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.submit_approval:
            {
                Toast.makeText(getActivity(), "submit_approval", Toast.LENGTH_SHORT).show();
                break;
            }

        }
    }
}
