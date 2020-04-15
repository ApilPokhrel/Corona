package com.rumsan.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
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
        TextView rm = findViewById(R.id.title);
        Spanned sp = Html.fromHtml("Special thanks to <a href='https://hamrolifebank.com/'><i>Hamro</i> Lifebank</a> team for sharing hospital data with us");
        hm.setText(sp);
        hm.setMovementMethod(LinkMovementMethod.getInstance());


        Spanned sp2 = Html.fromHtml("Developed by <a href='https://rumsan.com/'>Rumsan Associates</a> in association with <a href='https://askbhunte.com/'>Askbhunte</a>");
        rm.setText(sp2);
        rm.setMovementMethod(LinkMovementMethod.getInstance());


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
