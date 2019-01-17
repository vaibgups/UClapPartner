package your.service.com.partner.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import your.service.com.partner.R;
import your.service.com.partner.activities.LeadDetailsActivity;
import your.service.com.partner.model.lead.LeadItem;
import your.service.com.partner.model.lead.UserDetailsItem;

public class LeadAdapter extends RecyclerView.Adapter<LeadAdapter.ViewHolder> {
    private Context context;
    private List<LeadItem> leadItemList;
    private List<LeadItem> leadItemListAll;

    public LeadAdapter(Context context, List<LeadItem> leadItemList) {
        this.context = context;
        this.leadItemList = leadItemList;

    }

    @Override
    public LeadAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lead_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LeadAdapter.ViewHolder holder, int position) {

        LeadItem leadItem = leadItemList.get(position);
//        String uniqueID  = leadItem.getUniqeId();
//        String id = leadItem.getId();


        setWidgetValue(leadItem,holder,position);

    }

    private void setWidgetValue(final LeadItem leadItem, ViewHolder holder, int position) {

        List<UserDetailsItem> userDetailsItemList = leadItem.getUserDetails();
        UserDetailsItem userDetailsItem = userDetailsItemList.get(0);
        holder.service_request_customer_name.setText(userDetailsItem.getUserName());
        holder.service_request_name.setText(leadItem.getService().get(0).getService());
        holder.is_customer_status_active.setText(leadItem.getService().get(0).getServiceStatus());
        holder.customer_address.setText(leadItem.getServiceLocation());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,LeadDetailsActivity.class);
                intent.putExtra(LeadItem.class.getSimpleName(), (Serializable) leadItem);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return leadItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView service_request_customer_name, service_request_name, is_customer_status_active,customer_address;

        public ViewHolder(View itemView) {
            super(itemView);
            service_request_customer_name = (TextView)itemView.findViewById(R.id.service_request_customer_name);
            service_request_name = (TextView)itemView.findViewById(R.id.service_request_name);
            is_customer_status_active = (TextView)itemView.findViewById(R.id.is_customer_status_active);
            customer_address = (TextView) itemView.findViewById(R.id.customer_address);
        }
    }
}
