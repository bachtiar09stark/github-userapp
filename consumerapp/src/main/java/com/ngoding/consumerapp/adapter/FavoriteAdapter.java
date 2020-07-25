package com.ngoding.consumerapp.adapter;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ngoding.consumerapp.R;
import com.ngoding.consumerapp.database.Favorite;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    private Cursor mCursor;

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_user_list, parent, false);
        return new FavoriteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteViewHolder holder, int position) {
        if (mCursor.moveToPosition(position)) {
            holder.tvUsername.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(Favorite.COLUMN_USERNAME)));
        }
    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    public interface OnItemClickCallback {
        void onItemClicked(Favorite data);
    }

    public void setFavorites(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
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
