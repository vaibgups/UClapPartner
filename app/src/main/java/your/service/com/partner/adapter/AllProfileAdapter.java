package your.service.com.partner.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import your.service.com.partner.R;
import your.service.com.partner.activities.ProfileActivity;
import your.service.com.partner.model.partner.profile.SubcategoryItem;

public class AllProfileAdapter extends RecyclerView.Adapter<AllProfileAdapter.ViewHolder>
{
    ImageLoader imageLoader;
    Context context;
    List<ProfileData> allProfileData;
    ProfileData profileData;
    SubcategoryItem subcategoryItem;
    private List<SubcategoryItem> subcategoryItemList;

    public AllProfileAdapter(Context context, List<SubcategoryItem> subcategoryItemList) {
        this.context = context;
        this.subcategoryItemList = subcategoryItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_profile_details,null);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        subcategoryItem = subcategoryItemList.get(position);
        holder.category_name.setText(subcategoryItem.getSubCategory());
        holder.user_name.setText(subcategoryItem.getPartnerName());

       // imageLoader = ImageAdapter.getInstance(context).getImageLoader();
      //  holder.profile_image.setImageUrl(subcategoryItem.getPartnerImg(),imageLoader);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ProfileActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subcategoryItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView profile_image;
        TextView user_name,category_name;
        public ViewHolder(View itemView)
        {
            super(itemView);

            profile_image = itemView.findViewById(R.id.user_image);
            user_name = itemView.findViewById(R.id.user_name);
            category_name = itemView.findViewById(R.id.category_name);
        }
    }
}
