package com.example.slidetodelete;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    ArrayList<DataModel> arrData;
    OnDeleteListener onDeleteListener;
    private int swipedPosition = RecyclerView.NO_POSITION;

    Adapter(Context context, ArrayList<DataModel> arrData, OnDeleteListener onDeleteListener){
        this.context = context;
        this.arrData = arrData;
        this.onDeleteListener = onDeleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name_txt.setText(String.format("Name: %s", arrData.get(position).name));
        Log.e("InAdapter", "Name > " + arrData.get(position).name);
    }

    @Override
    public int getItemCount() {
        return arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name_txt;
        Button btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name_txt = itemView.findViewById(R.id.name_TV);
            btn = itemView.findViewById(R.id.delete_Btn);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onDeleteListener != null){
                        onDeleteListener.onDelete(position);
                    }
                }
            });
        }

    }

    public void setSwipedPosition(int position) {
        swipedPosition = position;
    }

    public interface OnDeleteListener {
        void onDelete(int position);
    }
}
