package com.rumsan.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SymptomActivity extends AppCompatActivity {

    TextView sm, pm, tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom);
        sm = findViewById(R.id.s_more);
        pm = findViewById(R.id.p_more);
        tm = findViewById(R.id.t_more);

        sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SymptomActivity.this, BrowserActivity.class);
                intent.putExtra("url", "https://nepalcorona.info/symptoms");
                startActivity(intent);
            }
        });

        pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SymptomActivity.this, BrowserActivity.class);
                intent.putExtra("url", "https://nepalcorona.info/prevention");
                startActivity(intent);
            }
        });

        tm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SymptomActivity.this, BrowserActivity.class);
                intent.putExtra("url", "https://nepalcorona.info/treatment");
                startActivity(intent);
            }
        });
    }
}
