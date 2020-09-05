package com.example.svasthik_gymowner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ExampleViewHolder> {
    private ArrayList<HistoryModelClass> mExampleList;
    private HistoryAdapter.OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(HistoryAdapter.OnItemClickListener listener) {
        mListener = listener;
    }



    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView date;

        public TextView id;
        public TextView time;
        public TextView price;
        public ImageView mImageView;


        public ExampleViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            id = itemView.findViewById(R.id.id);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            price = itemView.findViewById(R.id.price);
            mImageView=itemView.findViewById(R.id.image_history);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });


        }
    }
    public HistoryAdapter(ArrayList<HistoryModelClass> exampleList) {
        mExampleList = exampleList;

    }
    @NonNull
    @Override
    public HistoryAdapter.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_card_design, parent, false);
        HistoryAdapter.ExampleViewHolder evh = new HistoryAdapter.ExampleViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(HistoryAdapter.ExampleViewHolder holder, int position) {
        HistoryModelClass currentItem = mExampleList.get(position);
        holder.name.setText(currentItem.getCustomerName());
        holder.date.setText(currentItem.getDate());
        holder.id.setText(currentItem.getCustomerId());
        holder.time.setText(currentItem.getSlot());
        holder.price.setText(currentItem.getPrice());




    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

}
