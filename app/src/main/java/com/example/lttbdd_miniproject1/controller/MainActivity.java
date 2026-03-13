package com.example.lttbdd_miniproject1.controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lttbdd_miniproject1.R;
import com.example.lttbdd_miniproject1.adapter.RoomAdapter;
import com.example.lttbdd_miniproject1.model.Room;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RoomAdapter.OnItemClickListener {

    private List<Room> roomList;
    private RoomAdapter adapter;
    private RecyclerView rvRooms;
    private FloatingActionButton fabAdd;

    private final ActivityResultLauncher<Intent> roomActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Room room = (Room) result.getData().getSerializableExtra("ROOM");
                    int position = result.getData().getIntExtra("POSITION", -1);

                    if (position == -1) {
                        // Create
                        roomList.add(room);
                        adapter.notifyItemInserted(roomList.size() - 1);
                    } else {
                        // Update
                        roomList.set(position, room);
                        adapter.notifyItemChanged(position);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roomList = new ArrayList<>();
        // Sample data
        roomList.add(new Room("101", "Room 101", 1500000, false, "", ""));
        roomList.add(new Room("102", "Room 102", 2000000, true, "Nguyen Van A", "0987654321"));

        rvRooms = findViewById(R.id.rvRooms);
        fabAdd = findViewById(R.id.fabAdd);

        adapter = new RoomAdapter(roomList, this);
        rvRooms.setLayoutManager(new LinearLayoutManager(this));
        rvRooms.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RoomActivity.class);
            roomActivityResultLauncher.launch(intent);
        });
    }

    @Override
    public void onItemClick(Room room, int position) {
        Intent intent = new Intent(MainActivity.this, RoomActivity.class);
        intent.putExtra("ROOM", room);
        intent.putExtra("POSITION", position);
        roomActivityResultLauncher.launch(intent);
    }

    @Override
    public void onDeleteClick(Room room, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa phòng")
                .setMessage("Bạn có chăc chắn muốn xóa phòng trọ này?: " + room.getName() + "?")
                .setPositiveButton("Có", (dialog, which) -> {
                    roomList.remove(position);
                    adapter.notifyItemRemoved(position);
                })
                .setNegativeButton("Không", null)
                .show();
    }
}
