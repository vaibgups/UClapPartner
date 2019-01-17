package your.service.com.partner.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import your.service.com.partner.LoginActivity;
import your.service.com.partner.R;
import your.service.com.partner.SplashActivity;


public class AccountSettingActivity extends AppCompatActivity implements View.OnClickListener
{
    ImageView back;
    RelativeLayout rl1,rl2,rl3,rl4;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Account Settings");

        rl1 = findViewById(R.id.rl1);
//        rl2 = findViewById(R.id.rl2);
//        rl3 = findViewById(R.id.rl3);
        rl4 = findViewById(R.id.rl4);

        rl1.setOnClickListener(this);
//        rl2.setOnClickListener(this);
//        rl3.setOnClickListener(this);
        rl4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();

        switch (id)
        {
            case R.id.rl1:
            {

               // Toast.makeText(this, "rl1", Toast.LENGTH_SHORT).show();
                break;
            }
//            case R.id.rl2:
//            {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"provider@urbanclap.com"});
//                startActivity(intent);
//                break;
//            }
//            case R.id.rl3:
//            {
//                LayoutInflater layoutInflater = LayoutInflater.from(AccountSettingActivity.this);
//                View promptView = layoutInflater.inflate(R.layout.input_layout, null);
//
//                final AlertDialog.Builder builder  = new AlertDialog.Builder(this);
//                builder.setCancelable(false);
//                builder.setTitle("Enter your referal code");
//                builder.setView(promptView);
//
//                final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
////                final EditText input = new EditText(AccountSettingActivity.this);
////                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
////                        LinearLayout.LayoutParams.MATCH_PARENT,
////                        LinearLayout.LayoutParams.MATCH_PARENT);
////                input.setLayoutParams(lp);
////                builder.setView(input);
//
//                builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which)
//                    {
//                        Toast.makeText(AccountSettingActivity.this, "Please Enter Valid Referal code", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//                    }
//                });
//                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                builder.show();
//                break;
//            }
            case R.id.rl4:
            {
             //   Toast.makeText(this, "User Logout", Toast.LENGTH_SHORT).show();
                // Clearing all data from Shared Preferences

                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AccountSettingActivity.this);
                editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                // After logout redirect user to Loing Activity
                Intent i = new Intent(AccountSettingActivity.this, SplashActivity.class);


                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // Closing all the Activities
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // Staring Login Activity
                startActivity(i);

                finish();
                break;
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

}
