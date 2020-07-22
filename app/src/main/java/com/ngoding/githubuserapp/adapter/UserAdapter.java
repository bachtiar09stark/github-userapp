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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewModel> {

    private ArrayList<User> mData = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public UserAdapter() {
    }

    public void setData(ArrayList<User> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public UserViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_user_list, parent, false);
        return new UserViewModel(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserViewModel holder, final int position) {
        holder.tvUsername.setText(mData.get(position).getUsername());
        Glide.with(holder.itemView.getContext())
                .load(mData.get(position).getAvatar())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.imgAvatar);

        holder.cvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(mData.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnItemClickCallback {
        void onItemClicked(User data);
    }

    public static class UserViewModel extends RecyclerView.ViewHolder {
        TextView tvUsername;
        ImageView imgAvatar;
        CardView cvUser;

        public UserViewModel(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tv_username);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            cvUser = itemView.findViewById(R.id.cv_user);
        }
    }
}
