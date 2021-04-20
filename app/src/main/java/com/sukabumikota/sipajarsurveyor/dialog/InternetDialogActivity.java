package com.sukabumikota.sipajarsurveyor.dialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sukabumikota.sipajarsurveyor.MainActivity;
import com.sukabumikota.sipajarsurveyor.R;

public class InternetDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_dialog);
    }
    public void onClose(View view) {
        Intent back=new Intent(InternetDialogActivity.this, MainActivity.class);
        startActivity(back);
    }
}