package com.wtechweb.v2_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvCount;
    EditText etName, etCell;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = findViewById(R.id.etName);
        etCell = findViewById(R.id.etCell);
        tvCount = findViewById(R.id.tvCount);
        btnSubmit =findViewById(R.id.btnSubmit);

        etCell.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String temp = charSequence.toString();

                if(temp.length()<=11)
                {
                    tvCount.setBackgroundColor(Color.WHITE);
                    btnSubmit.setVisibility(View.VISIBLE);
                    if(temp.length()==11)
                    {
                        tvCount.setBackgroundColor(Color.GREEN);
                    }
                }
                else
                {
                    tvCount.setBackgroundColor(Color.RED);
                    btnSubmit.setVisibility(View.GONE);
                }

                tvCount.setText(temp.length()+"/11");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void btnSubmit(View v)
    {

        if(etName.getText().toString().isEmpty())
        {
            etName.setError("Name cant be empty");
        }
        ContactsDB db = new ContactsDB(this);
        db.open();
        db.insertData(etName.getText().toString().trim(), etCell.getText().toString().trim());
        db.close();
    }
    public void btnShowData(View v)
    {
        startActivity(new Intent(MainActivity.this, ShowData.class));
    }
    public void btnEditData(View v)
    {
        ContactsDB db = new ContactsDB(this);
        db.open();
        long count = db.updateEntry("1","CS Head", "03234677035");
        Toast.makeText(this, ""+count, Toast.LENGTH_SHORT).show();
        db.close();
    }
    public void btnDeleteData(View v)
    {
        ContactsDB db = new ContactsDB(this);
        db.open();
        long count = db.deleteEntry("1");
        Toast.makeText(this, ""+count, Toast.LENGTH_SHORT).show();
        db.close();
    }
}