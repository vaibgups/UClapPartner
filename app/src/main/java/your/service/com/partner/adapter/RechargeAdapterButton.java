package your.service.com.partner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import your.service.com.partner.R;
import your.service.com.partner.model.recharge.DataItem;

public class RechargeAdapterButton extends RecyclerView.Adapter<RechargeAdapterButton.ViewHolder> {


    private Context context;
    private List<DataItem> dataItemList;
    private ClickListenerInterface clickListenerInterface;

    public RechargeAdapterButton(Context context, List<DataItem> dataItemList, ClickListenerInterface clickListenerInterface) {
        this.context = context;
        this.dataItemList = dataItemList;
        this.clickListenerInterface = clickListenerInterface;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        DataItem dataItem = dataItemList.get(position);

        setWidgetValue(dataItem,holder,position);
        holder.recharge_button_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListenerInterface.onItemClick(position,view);
            }
        });


    }

    private void setWidgetValue(DataItem dataItem, ViewHolder holder, int position) {

        holder.recharge_amount_tv.setText(dataItem.getSPrice());
        holder.credits_number_score.setText(dataItem.getSPoint());
    }

    @Override
    public int getItemCount() {
        return dataItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView recharge_amount_symbol_tv, recharge_amount_tv,credits_number_score, credits;
        LinearLayout recharge_button_ll;
        public ViewHolder(View itemView) {
            super(itemView);
            recharge_amount_symbol_tv = (TextView) itemView.findViewById(R.id.recharge_amount_symbol_tv);
            recharge_amount_tv = (TextView) itemView.findViewById(R.id.recharge_amount_tv);
            credits_number_score = (TextView) itemView.findViewById(R.id.credits_number_score);
            credits = (TextView) itemView.findViewById(R.id.credits);
            recharge_button_ll = (LinearLayout) itemView.findViewById(R.id.recharge_button_ll);
        }
    }

    public interface ClickListenerInterface {
        void onItemClick(int position, View v);
    }
}
