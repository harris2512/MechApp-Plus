package com.project.readyassist_mechapp.screen.activity.vendor_skill.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.project.readyassist_mechapp.R;

import java.util.Random;


public class PopularServicesAdapter extends RecyclerView.Adapter<PopularServicesAdapter.MyViewHolder> {

    protected Context context;

    protected String[] arrayServices;

    protected MyViewHolder mHolder;
    protected int mPosition;


    private int thisPosition;
    protected RecyclerView.ViewHolder mViewHolder;

    public PopularServicesAdapter(Context context, String[] arrayServices) {
        this.context = context;
        this.arrayServices = arrayServices;

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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_popular_services_list_item,
                parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        mViewHolder = holder;
        mHolder = holder;
        mPosition = holder.getAdapterPosition();

//        Random rnd = new Random();
//        int currentColor = Color.argb(35, rnd.nextInt(270), rnd.nextInt(276),
//                rnd.nextInt(175));
//        holder.layout_popular_services_item.setBackgroundColor(currentColor);


        holder.tv_popular_services_item.setText(arrayServices[position]);

        if (getThisPosition() != -1 && position == getThisPosition()) {
            holder.layout_popular_services_item.setBackground(ResourcesCompat.getDrawable(context.getResources(),
                    R.drawable.bg_selected_primary_corner, null));
        } else {
            holder.layout_popular_services_item.setBackground(ResourcesCompat.getDrawable(context.getResources(),
                    R.drawable.bg_unselected_primary_corner, null));
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
        return arrayServices.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        protected ConstraintLayout layout_popular_services_item;
        protected ImageView img_popular_services_item;
        protected TextView tv_popular_services_item;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            layout_popular_services_item = itemView.findViewById(R.id.layout_popular_services_item);
            img_popular_services_item = itemView.findViewById(R.id.img_popular_services_item);
            tv_popular_services_item = itemView.findViewById(R.id.tv_popular_services_item);



        }

    }


}
