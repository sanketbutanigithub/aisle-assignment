package com.example.aisle_assignment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.aisle_assignment.R;
import com.example.aisle_assignment.models.Profile;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.ViewClass> {

    Context context;
    List<Profile> profileList;

    public LikeAdapter(Context context, List<Profile> profileList) {
        this.context = context;
        this.profileList = profileList;
    }

    @NonNull
    @Override
    public LikeAdapter.ViewClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.likes_item, parent, false);
        ViewClass viewClass = new ViewClass(view);
        return viewClass;
    }

    @Override
    public void onBindViewHolder(@NonNull LikeAdapter.ViewClass holder, int position) {
        holder.txtname.setText(profileList.get(position).getFirst_name());
        Glide.with(context).load(profileList.get(position).getAvatar()).apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3))).into(holder.imgprofile);
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    public class ViewClass extends RecyclerView.ViewHolder {
        RoundedImageView imgprofile;
        TextView txtname;

        public ViewClass(@NonNull View itemView) {
            super(itemView);
            imgprofile = itemView.findViewById(R.id.imgprofile);
            txtname = itemView.findViewById(R.id.txtname);
        }
    }
}
