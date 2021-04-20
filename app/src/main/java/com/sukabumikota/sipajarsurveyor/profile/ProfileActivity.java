package com.sukabumikota.sipajarsurveyor.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sukabumikota.sipajarsurveyor.MainActivity;
import com.sukabumikota.sipajarsurveyor.R;
import com.sukabumikota.sipajarsurveyor.auth.SharedPrafManager;

public class ProfileActivity extends AppCompatActivity {
    TextView tvprofileusername,tvprofiletgldaftar,tvprofilenama,tvprofilnpwpd,tvprofileusernameinfo,tvprofilealamat;
    SharedPrafManager sharedPrefManager; // ini

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPrefManager = new SharedPrafManager(this); // ini
        tvprofileusername = findViewById(R.id.txtprofileusername);
        tvprofilenama = findViewById(R.id.txtprofilenama);
        tvprofileusernameinfo = findViewById(R.id.txtprofileusernameinfo);

        tvprofileusername.setText(sharedPrefManager.getSPEmail());
        tvprofilenama.setText(sharedPrefManager.getSPNama() + sharedPrefManager.getSpNamaBelakang());
        tvprofileusernameinfo.setText(sharedPrefManager.getSPEmail());
    }
    public void onBackClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    public void edit(View view) {
        startActivity(new Intent(this, EditProfileActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
}
