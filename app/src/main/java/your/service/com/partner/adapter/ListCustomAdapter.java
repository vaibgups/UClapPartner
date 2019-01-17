package your.service.com.partner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import your.service.com.partner.R;


public class ListCustomAdapter extends BaseAdapter
{
    Context context;
    String countryList1[];
    int flags1[];
    LayoutInflater inflter;

    public ListCustomAdapter(Context applicationContext, String[] countryList1, int[] flags1)
    {
        this.context = context;
        this.countryList1 = countryList1;
        this.flags1 = flags1;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return countryList1.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        view = inflter.inflate(R.layout.listview1_custom_layout, null);
        TextView country = (TextView) view.findViewById(R.id.credit_text);
        ImageView icon = (ImageView) view.findViewById(R.id.icon_list);
        country.setText(countryList1[i]);
        icon.setImageResource(flags1[i]);
        return view;

    }
}
