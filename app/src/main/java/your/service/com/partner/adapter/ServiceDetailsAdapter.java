package your.service.com.partner.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import your.service.com.partner.R;
import your.service.com.partner.activities.LeadDetailsActivity;
import your.service.com.partner.model.lead.LeadItem;
import your.service.com.partner.model.lead.QuesAnsItem;
import your.service.com.partner.model.lead.ServiceItem;
import your.service.com.partner.model.lead.UserDetailsItem;

public class ServiceDetailsAdapter  extends RecyclerView.Adapter<ServiceDetailsAdapter.ViewHolder> {
    private Context context;
    private List<ServiceItem> serviceItemList;

    public ServiceDetailsAdapter(Context context, List<ServiceItem> serviceItemList) {
        this.context = context;
        this.serviceItemList = serviceItemList;
    }

    @Override
    public ServiceDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_details_layout, parent, false);
        ServiceDetailsAdapter.ViewHolder viewHolder = new ServiceDetailsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ServiceDetailsAdapter.ViewHolder holder, int position) {

        ServiceItem serviceItem = serviceItemList.get(position);

        setWidgetValue(serviceItem,holder,position);

    }

    private void setWidgetValue(final ServiceItem serviceItem, ServiceDetailsAdapter.ViewHolder holder, int position) {

        holder.question_requirement.setText(serviceItem.getQuestion());
        holder.client_requirement.setText(serviceItem.getAttribute());

//        List<UserDetailsItem> userDetailsItemList = leadItem.getUserDetails();
//        UserDetailsItem userDetailsItem = userDetailsItemList.get(0);
//        holder.service_request_customer_name.setText(userDetailsItem.getUserName());
//        holder.service_request_name.setText(leadItem.getService());
//        holder.is_customer_status_active.setText(leadItem.getServiceStatus());
//        holder.customer_address.setText(leadItem.getLocation());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context,LeadDetailsActivity.class);
//                intent.putExtra(LeadItem.class.getSimpleName(),leadItem);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return serviceItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView question_requirement, client_requirement;

        public ViewHolder(View itemView) {
            super(itemView);
            question_requirement = (TextView)itemView.findViewById(R.id.question_requirement);
            client_requirement = (TextView)itemView.findViewById(R.id.client_requirement);

        }
    }
}
