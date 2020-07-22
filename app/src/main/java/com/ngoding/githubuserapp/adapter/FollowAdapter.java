package com.ngoding.githubuserapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ngoding.githubuserapp.R;
import com.ngoding.githubuserapp.model.User;

import java.util.ArrayList;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.FollowViewModel> {

    ArrayList<User> mData = new ArrayList<>();

    public FollowAdapter() {
    }

    public void setData(ArrayList<User> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FollowViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_user_list, parent, false);
        return new FollowViewModel(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowViewModel holder, int position) {
        holder.tvUsername.setText(mData.get(position).getUsername());
        Glide.with(holder.itemView.getContext())
                .load(mData.get(position).getAvatar())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.imgAvatar);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class FollowViewModel extends RecyclerView.ViewHolder {
        TextView tvUsername;
        ImageView imgAvatar;
        CardView cvUser;

        public FollowViewModel(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tv_username);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            cvUser = itemView.findViewById(R.id.cv_user);
        }
    }
}
