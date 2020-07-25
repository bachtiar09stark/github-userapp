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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ngoding.consumerapp.MainActivity;
import com.ngoding.consumerapp.R;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

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
            holder.tvUsername.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(MainActivity.COLUMN_USERNAME)));
            Glide.with(holder.itemView.getContext())
                    .load(mCursor.getString(mCursor.getColumnIndexOrThrow(MainActivity.COLUMN_AVATAR)))
                    .apply(new RequestOptions().override(55, 55))
                    .into(holder.imgAvatar);
        }
    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
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
