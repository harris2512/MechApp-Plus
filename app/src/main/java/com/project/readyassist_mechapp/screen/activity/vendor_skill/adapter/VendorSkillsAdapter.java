package com.project.readyassist_mechapp.screen.activity.vendor_skill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.readyassist_mechapp.R;


public class VendorSkillsAdapter extends RecyclerView.Adapter<VendorSkillsAdapter.MyViewHolder> {

    protected Context context;


    protected MyViewHolder mHolder;
    protected int mPosition;


    private int thisPosition;
    protected RecyclerView.ViewHolder mViewHolder;

    public VendorSkillsAdapter(Context context) {
        this.context = context;

    }

    private OnItemClickListener onRecyclerViewPaymentItemClickListener;

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    //Then define a return value method of type int
    public int getThisPosition() {
        return thisPosition - 1;
    }


    //This method is called at the place where this adapter is called, it will not be called in this adapter
    public void setThisPosition(int thisPosition) {
        this.thisPosition = thisPosition;
    }

    public void setOnRecyclerViewItemClickListener(OnItemClickListener onItemClickListener) {
        this.onRecyclerViewPaymentItemClickListener = onItemClickListener;
    }

    public int getItemPosition() {
        return thisPosition;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_vendor_skill_list_item,
                parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        mViewHolder = holder;
        mHolder = holder;
        mPosition = holder.getAdapterPosition();


        if (getThisPosition() != -1 && position == getThisPosition()) {

        } else {

        }


        if (onRecyclerViewPaymentItemClickListener != null) {
            //Click event
            mViewHolder.itemView.setOnClickListener(v -> {
                onRecyclerViewPaymentItemClickListener.onClick(position);
            });

            //Long press event
            mViewHolder.itemView.setOnLongClickListener(v -> {
                onRecyclerViewPaymentItemClickListener.onLongClick(position);
                return false;
            });
        }

    }


    @Override
    public int getItemCount() {
        return 6;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        protected TextView tv_date_vendor_skill_item, tv_name_vendor_skill_item,
                tv_day_charge_vendor_skill_item, tv_night_charge_vendor_skill_item,
                tv_terrain_charge_vendor_skill_item, tv_remove_vendor_skill_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_date_vendor_skill_item = itemView.findViewById(R.id.tv_date_vendor_skill_item);
            tv_name_vendor_skill_item = itemView.findViewById(R.id.tv_name_vendor_skill_item);
            tv_day_charge_vendor_skill_item = itemView.findViewById(R.id.tv_day_charge_vendor_skill_item);
            tv_night_charge_vendor_skill_item = itemView.findViewById(R.id.tv_night_charge_vendor_skill_item);
            tv_terrain_charge_vendor_skill_item = itemView.findViewById(R.id.tv_terrain_charge_vendor_skill_item);
            tv_remove_vendor_skill_item = itemView.findViewById(R.id.tv_remove_vendor_skill_item);

        }

    }


}
