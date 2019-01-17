package your.service.com.partner.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import your.service.com.partner.R;
import your.service.com.partner.activities.AdditionalBusinessActivity;


public class RecyclerViewAdapter1 extends RecyclerView.Adapter<RecyclerViewAdapter1.ViewHolder>
{

    Context context;
    private int lastSelectedPosition = -1;
    List<DataAdapter> dataAdapters;
    DataAdapter dataAdapterOBJ;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public RecyclerViewAdapter1(List<DataAdapter> getDataAdapter, Context context){

        super();
        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_checkbox, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder Viewholder, final int position) {

        dataAdapterOBJ =  dataAdapters.get(position);
        Viewholder.cate_id.setText(dataAdapterOBJ.getCat_id());
        Viewholder.cate_name.setText(dataAdapterOBJ.getCate_name());
    //    Viewholder.selectionState.setChecked(lastSelectedPosition == position);
//       Viewholder.setIsRecyclable(false);


        Viewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                editor = preferences.edit();
                //  editor.putString("catID",""+cate_id.getText());
                editor.putString("category11",""+Viewholder.cate_name.getText());
                editor.commit();


                ((ViewHolder) Viewholder).selectionState.setChecked(!((ViewHolder) Viewholder).selectionState.isChecked());
                if (((ViewHolder) Viewholder).selectionState.isChecked())
                {
                    ((AdditionalBusinessActivity)context).visibleProceedButton1();

                    //   onItemClick.onItemCheck(dataAdapterOBJ);
                }
                else
                {
                    ((AdditionalBusinessActivity)context).visibleProceedButton11();
                    // onItemClick.onItemUncheck(dataAdapterOBJ);
                }
                //    Viewholder.selectionState.setChecked();
//                Viewholder.selectionState.setChecked(position.);

//                lastSelectedPosition = position;
//                notifyDataSetChanged();
//
//                ((Main2Activity)context).visibleProceedButton1();
                //   ((Activity)context)
                //   Toast.makeText(context, "selected offer is " + cate_id.getText(), Toast.LENGTH_SHORT).show();
                //   ((Main2Activity) context).visibleProceedButton1();
                // Toast.makeText(context, "heloo"+getItemId(), Toast.LENGTH_SHORT).show();

                //      Viewholder.selectionState.setChecked(true);

            }
        });

//        Viewholder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lastSelectedPosition = position;
//                notifyDataSetChanged();
//
//             //   Toast.makeText(context, "selected offer is " + cate_id.getText(), Toast.LENGTH_SHORT).show();
//                ((MainActivity) context).visibleProceedButton();
//               // Toast.makeText(context, "heloo"+getItemId(), Toast.LENGTH_SHORT).show();
//
//
//            }
//        });

//       if (position == lastSelectedPosition)
//       {
//           Viewholder.selectionState.setChecked(true);
//       }
//       else
//       {
//           Viewholder.selectionState.setChecked(false);
//       }
//       Viewholder.radioButtonOfferSelect.setText(dataAdapterOBJ.getCate_name());

//        Viewholder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainActivity) context).visibleProceedButton();
//
//            }
//        });

        //Viewholder.radioButtonOfferSelect.setChecked(lastSelectedPosition == position);

//       Viewholder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainActivity) context).visibleProceedButton();
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return dataAdapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView cate_id,cate_name;

        public RadioButton radioButtonOfferSelect;

        public RelativeLayout relativeLayout;

        public RadioGroup radioGroup;
        public LinearLayout clickThemain;
        public CheckBox selectionState;

        public ViewHolder(View itemView) {

            super(itemView);

            cate_id = (TextView) itemView.findViewById(R.id.id) ;
            cate_name = (TextView) itemView.findViewById(R.id.offer_amount_txt);

//            radioButtonOfferSelect = (RadioButton) itemView.findViewById(R.id.radioButtonOfferSelect);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.item);
            clickThemain=(LinearLayout)itemView.findViewById(R.id.click);
            selectionState = (CheckBox) itemView.findViewById(R.id.offer_select);


            preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            editor = preferences.edit();
          //  editor.putString("catID",""+cate_id.getText());
            editor.putString("category11",""+cate_name.getText());
            editor.commit();

            //    radioGroup = (RadioGroup) itemView.findViewById(R.id.radioGroup);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    lastSelectedPosition = getAdapterPosition();
//                    notifyDataSetChanged();
//
//                    ((AdditionalBusinessActivity)context).visibleProceedButton1();
//
////                    preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
////                    editor = preferences.edit();
////                    editor.putString("catID",""+cate_id.getText());
////                    editor.commit();
////                    Toast.makeText(RecyclerViewAdapter1.this.context,
////                            "selected offer is " + cate_id.getText(),
////                            Toast.LENGTH_SHORT).show();
////                    selectionState.setChecked(false);
//                }
//            });
//            selectionState.setClickable(false);

        }
    }

    public DataAdapter getData()
    {
        return dataAdapterOBJ;
    }
}
