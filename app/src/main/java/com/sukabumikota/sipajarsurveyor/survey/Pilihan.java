package com.sukabumikota.sipajarsurveyor.survey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.sukabumikota.sipajarsurveyor.R;
import com.sukabumikota.sipajarsurveyor.objekpajak.OPActivity;

public class Pilihan extends AppCompatActivity {
CardView CVSurvey, CVRiwayat, CVOp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihan);
        CVSurvey = findViewById(R.id.cvsurvey);
        CVRiwayat = findViewById(R.id.cvriwayat);
        CVOp = findViewById(R.id.cvop);

        CVSurvey.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent menu = new Intent(Pilihan.this, SurveyActivity.class);
                        startActivity(menu);
                    }
                }
        );

        CVRiwayat.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent menu = new Intent(Pilihan.this, RiwayatSurveyActivity.class);
                        startActivity(menu);
                    }
                }
        );

        CVOp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent menu = new Intent(Pilihan.this, OPActivity.class);
                        startActivity(menu);
                    }
                }
        );

    }
}
