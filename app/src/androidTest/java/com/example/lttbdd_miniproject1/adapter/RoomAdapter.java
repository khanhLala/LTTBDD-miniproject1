package com.example.lttbdda_miniproject1.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lttbdda_miniproject1.R;
import com.example.lttbdda_miniproject1.model.Room;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<Room> roomList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Room room, int position);
        void onDeleteClick(Room room, int position);
    }

    public RoomAdapter(List<Room> roomList, OnItemClickListener listener) {
        this.roomList = roomList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.tvRoomName.setText(room.getName());
        holder.tvPrice.setText("Price: " + String.format("%,.0f", room.getPrice()) + " VND");
        
        if (room.isRented()) {
            holder.tvStatus.setText("Status: Rented (" + room.getRenterName() + ")");
            holder.tvStatus.setTextColor(Color.RED);
        } else {
            holder.tvStatus.setText("Status: Empty");
            holder.tvStatus.setTextColor(Color.GREEN);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(room, position));
        holder.ivDelete.setOnClickListener(v -> listener.onDeleteClick(room, position));
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomName, tvPrice, tvStatus;
        ImageView ivDelete;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }
}
