package com.craftylyteam.craftylyapp1.main.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class BulbAdapter extends RecyclerView.Adapter<BulbAdapter.BulbHolder>{
    private List<Bulb> bulbs = new ArrayList<>();
    private List<LinearLayoutCompat>linearLayoutList = new ArrayList<>();

    private OnBulbListener mOnBulbListener;

    public BulbAdapter(List<Bulb> bulbsList, OnBulbListener onBulbListener) {

        bulbs = bulbsList;
        this.mOnBulbListener = onBulbListener;
    }

    @NonNull
    @Override
    public BulbHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bulb_item, parent, false);
        return new BulbHolder(itemView, mOnBulbListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BulbHolder holder, int position) {
        Bulb currentBulb = bulbs.get(position);
        ImageView bulb = holder.imageViewBulbChoice;
        Context context = bulb.getContext();

        bulb.setTag(currentBulb.getChoice());
        switch (currentBulb.getChoice()) {
            case Constants.CALICO_BULB:
                bulb.setImageResource(R.drawable.ic_calico_bulb_icon);
                break;
            case Constants.CRAFTYLY_CALICO_BULB:
                bulb.setImageResource(R.drawable.ic_craftyly_calico_icon);
                break;
            case Constants.LEMON_BULB:
                bulb.setImageResource(R.drawable.ic_lemon_bulb_icon);
                break;
            case Constants.SUNSET_BULB:
                bulb.setImageResource(R.drawable.ic_sunset_bulb_icon);
                break;
            case Constants.CAMO_BULB:
                bulb.setImageResource(R.drawable.ic_camo_bulb_icon);
                break;
            default:
                bulb.setImageResource(R.drawable.ic_craftyly_bulb_icon);
                break;
        }

        if (!linearLayoutList.contains(holder.linearLayoutBulbItem)) {
            linearLayoutList.add(holder.linearLayoutBulbItem);
        }

    }

    @Override
    public int getItemCount() {
        return bulbs.size();
    }

    class BulbHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageViewBulbChoice;
        private LinearLayoutCompat linearLayoutBulbItem;
        OnBulbListener onBulbListener;

        public BulbHolder(@NonNull View itemView, OnBulbListener onBulbListener) {
            super(itemView);
            imageViewBulbChoice = itemView.findViewById(R.id.image_view_bulb_choice);
            linearLayoutBulbItem = itemView.findViewById(R.id.linear_layout_bulb_item);
            this.onBulbListener = onBulbListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onBulbListener.onBulbClick(getAdapterPosition());

        }
    }

    public interface OnBulbListener{
        void onBulbClick(int position);
    }
}
