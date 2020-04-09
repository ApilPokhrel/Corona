package com.rumsan.corona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.rumsan.corona.adapter.WorldDataRecyclerAdapter;
import com.rumsan.corona.entity.WorldDataModel;

import java.util.List;

public class DataActivity extends AppCompatActivity {

    RecyclerView worldData;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        worldData = findViewById(R.id.worldData);
        search = findViewById(R.id.search);

        Intent intent = getIntent();
        final List<WorldDataModel> datas = (List<WorldDataModel>) intent.getSerializableExtra("datas");
        final WorldDataRecyclerAdapter adapter = new WorldDataRecyclerAdapter(this, datas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        worldData.setLayoutManager(linearLayoutManager);
        worldData.setAdapter(adapter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
                return;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
