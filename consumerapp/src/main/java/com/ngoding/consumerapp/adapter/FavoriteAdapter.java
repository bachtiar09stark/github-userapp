package com.ngoding.consumerapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ngoding.consumerapp.R;
import com.ngoding.consumerapp.database.Favorite;

public class FavoriteAdapter extends ListAdapter<Favorite, FavoriteAdapter.FavoriteViewHolder> {

    private static final DiffUtil.ItemCallback<Favorite> DIFF_CALLBACK = new DiffUtil.ItemCallback<Favorite>() {
        @Override
        public boolean areItemsTheSame(@NonNull Favorite oldItem, @NonNull Favorite newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Favorite oldItem, @NonNull Favorite newItem) {
            return oldItem.getUsername().equals(newItem.getUsername()) &&
                    oldItem.getAvatar().equals(newItem.getAvatar());
        }
    };
    private OnItemClickCallback onItemClickCallback;

    public FavoriteAdapter() {
        super(DIFF_CALLBACK);
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public Favorite getFavoriteAt(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_user_list, parent, false);
        return new FavoriteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteViewHolder holder, int position) {
        final Favorite favorite = getItem(position);
        holder.tvUsername.setText(favorite.getUsername());
        Glide.with(holder.itemView.getContext())
                .load(favorite.getAvatar())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.imgAvatar);

        holder.cvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(getItem(holder.getAdapterPosition()));
            }
        });
    }

    public interface OnItemClickCallback {
        void onItemClicked(Favorite data);
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername;
        ImageView imgAvatar;
        CardView cvUser;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tv_username);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            cvUser = itemView.findViewById(R.id.cv_user);
        }
    }
}
