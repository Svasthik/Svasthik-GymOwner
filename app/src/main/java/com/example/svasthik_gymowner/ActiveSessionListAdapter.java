package com.example.svasthik_gymowner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActiveSessionListAdapter extends RecyclerView.Adapter<ActiveSessionListAdapter.ExampleViewHolder> {
    private ArrayList<ActiveSessionListModelClass> mExampleList;
    private ActiveSessionListAdapter.OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener( ActiveSessionListAdapter.OnItemClickListener listener) {
        mListener = listener;
    }



    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;


        public ExampleViewHolder(View itemView) {
            super(itemView);

            mTextView1 = itemView.findViewById(R.id.nameAS);
            mTextView2 = itemView.findViewById(R.id.idAS);
            mTextView3 = itemView.findViewById(R.id.timeAS);
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
    public ActiveSessionListAdapter(ArrayList<ActiveSessionListModelClass> exampleList) {
        mExampleList = exampleList;

    }
    @NonNull
    @Override
    public ActiveSessionListAdapter.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_session_card_design, parent, false);
        ActiveSessionListAdapter.ExampleViewHolder evh = new ActiveSessionListAdapter.ExampleViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(ActiveSessionListAdapter.ExampleViewHolder holder, int position) {
        ActiveSessionListModelClass currentItem = mExampleList.get(position);
        holder.mTextView1.setText(currentItem.getName());
        holder.mTextView2.setText(currentItem.getId());
        holder.mTextView3.setText(currentItem.getTime());




    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

}
