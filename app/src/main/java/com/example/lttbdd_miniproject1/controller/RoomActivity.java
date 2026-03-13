package com.example.lttbdd_miniproject1.controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lttbdd_miniproject1.R;
import com.example.lttbdd_miniproject1.model.Room;

public class RoomActivity extends AppCompatActivity {

    private EditText etId, etName, etPrice, etRenterName, etPhoneNumber;
    private CheckBox cbIsRented;
    private Button btnSave;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        initViews();

        Room room = (Room) getIntent().getSerializableExtra("ROOM");
        position = getIntent().getIntExtra("POSITION", -1);

        if (room != null) {
            etId.setText(room.getId());
            etId.setEnabled(false); // ID usually shouldn't change
            etName.setText(room.getName());
            etPrice.setText(String.valueOf(room.getPrice()));
            cbIsRented.setChecked(room.isRented());
            etRenterName.setText(room.getRenterName());
            etPhoneNumber.setText(room.getPhoneNumber());
        }

        btnSave.setOnClickListener(v -> saveRoom());
    }

    private void initViews() {
        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        cbIsRented = findViewById(R.id.cbIsRented);
        etRenterName = findViewById(R.id.etRenterName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnSave = findViewById(R.id.btnSave);
    }

    private void saveRoom() {
        String id = etId.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();
        boolean isRented = cbIsRented.isChecked();
        String renterName = etRenterName.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();

        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(name) || TextUtils.isEmpty(priceStr)) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin vào các ô", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);

        Room room = new Room(id, name, price, isRented, renterName, phoneNumber);
        
        Intent resultIntent = new Intent();
        resultIntent.putExtra("ROOM", room);
        resultIntent.putExtra("POSITION", position);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
