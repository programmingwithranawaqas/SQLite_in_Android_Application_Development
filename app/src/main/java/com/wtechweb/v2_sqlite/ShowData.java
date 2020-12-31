package com.wtechweb.v2_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ShowData extends AppCompatActivity {

    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        tvResult = findViewById(R.id.tvResult);

        ContactsDB db = new ContactsDB(this);
        db.open();
        tvResult.setText(db.getData());
        db.close();

    }
}