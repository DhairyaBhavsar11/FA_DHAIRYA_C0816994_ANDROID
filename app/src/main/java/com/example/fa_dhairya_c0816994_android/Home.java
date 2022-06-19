package com.example.fa_dhairya_c0816994_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class Home extends AppCompatActivity {

    DatabaseHandler db;
    TextView datalist;
    TextView datalist_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageButton add = findViewById(R.id.add_btn);
        ImageButton sync = findViewById(R.id.sync_btn);
        datalist = findViewById(R.id.all_data_list);
        datalist_count = findViewById(R.id.data_list_count);

        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }

        });

        add.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Home.this, MapsActivity.class);
                startActivity(intent);
                finish();
            }
        }));


    }

    public void refreshData() {
        datalist_count.setText("ALL DATA COUNT : " + db.getTotalCount());

        List<Place> placeList = db.getAlldatalist();
        datalist.setText("");
        for (Place place : placeList) {
            datalist.append("Place_ID : " + place.getId() + "\n" + "| Place_Name : " + place.getTitle() + "\n" + "| Place_latitude : " + place.getPlatitude() + "\n" + "| Place_Longitude : " + place.getPlongitude() + " \n\n");
        }
    }
}
