package com.rumsan.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CreditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        TextView hm = findViewById(R.id.hm_text);
        Spanned sp = Html.fromHtml("Special thanks to <i>Hamro</i> Lifebank team for sharing hospital data with us");
        hm.setText(sp);

        LinearLayout toolbar = findViewById(R.id.toolbar_layout);

        ImageView back = toolbar.findViewById(R.id.back);
        TextView title = toolbar.findViewById(R.id.bar_title);

        title.setText("About us");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}
