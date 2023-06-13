package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewProfileAdapter extends RecyclerView.Adapter<ViewProfileAdapter.ProfileView> {
    @NonNull
    @Override
    public ProfileView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileView profileView, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ProfileView extends RecyclerView.ViewHolder {
        public ProfileView(@NonNull View itemView) {
            super(itemView);
        }
    }
}
