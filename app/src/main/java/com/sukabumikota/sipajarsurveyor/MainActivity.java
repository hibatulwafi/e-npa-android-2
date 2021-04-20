package com.sukabumikota.sipajarsurveyor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.sukabumikota.sipajarsurveyor.apihelper.BaseApiService;
import com.sukabumikota.sipajarsurveyor.apihelper.koneksi;
import com.sukabumikota.sipajarsurveyor.auth.LoginActivity;
import com.sukabumikota.sipajarsurveyor.auth.SharedPrafManager;
import com.sukabumikota.sipajarsurveyor.objekpajak.OPActivity;
import com.sukabumikota.sipajarsurveyor.profile.EditProfileActivity;
import com.sukabumikota.sipajarsurveyor.profile.ProfileActivity;
import com.sukabumikota.sipajarsurveyor.survey.SurveyActivity;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.sukabumikota.sipajarsurveyor.bantuan.bantuan;
import com.sukabumikota.sipajarsurveyor.log.LogActivity;
import com.sukabumikota.sipajarsurveyor.survey.Pilihan;
import com.sukabumikota.sipajarsurveyor.wajibpajak.WPActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private long exitTime = 0;

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.banner, R.drawable.banner, R.drawable.banner, R.drawable.banner, R.drawable.banner};
    CardView CVSurveyTempat, CVWajibPajak , CVBantuan,CVLog;
    private BottomNavigationView bottomNavigationView;
    Context mContext;
    String email;
    BaseApiService mApiService;
    SharedPrafManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPrefManager = new SharedPrafManager(this);
        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        CVSurveyTempat=findViewById(R.id.surveytempat);
        CVWajibPajak=findViewById(R.id.wajibpajak);
        CVBantuan=findViewById(R.id.bantu);
        CVLog=findViewById(R.id.log);

        // buat cek password
        mContext = this;
        mApiService = koneksi.getAPIService();
        cekpassword();

        //navigation
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.navigationHome);

        CVSurveyTempat.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent menu = new Intent(MainActivity.this, Pilihan.class);
                        startActivity(menu);
                    }
                }
        );

        CVWajibPajak.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent menu = new Intent(MainActivity.this, WPActivity.class);
                        startActivity(menu);
                    }
                }
        );


        CVBantuan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent menu = new Intent(MainActivity.this, bantuan.class);
                        startActivity(menu);
                    }
                }
        );

        CVLog.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent menu = new Intent(MainActivity.this, LogActivity.class);
                        startActivity(menu);
                    }
                }
        );
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
            verifyExit();
        } else {
            finish();

        }
    }

    private void verifyExit() {
        new MaterialStyledDialog.Builder(this)
                .setTitle("Yakin Logout?")
                .setDescription("Anda akan keluar dari sesi.\n" +
                        "Klik Ya untuk logout!")
                .setIcon(R.drawable.li_exit)
                .setHeaderDrawable(R.drawable.bg_gradient_orange)
                .withDialogAnimation(true)
                .withIconAnimation(true)
                .setCancelable(false)
                .setPositiveText("Ya")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        endSession();
                    }
                })
                .setNegativeText("Kembali")
                .show();
    }

    public void endSession() {
        sharedPrefManager.saveSPBoolean(sharedPrefManager.SP_SUDAH_LOGIN, false);
        startActivity(new Intent(MainActivity.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigationHome:
                    return true;
                case  R.id.navigationMyProfile:
                    Intent tentang = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(tentang);
                    return true;
                case  R.id.navigationMenu:
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.openDrawer(GravityCompat.START);
                    return true;
            }
            return false;
        }
    };

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_titiksumur) {
            Intent navdirect = new Intent(MainActivity.this, OPActivity.class);
            startActivity(navdirect);
        } else if (id == R.id.nav_bantu) {
            Intent navdirect = new Intent(MainActivity.this, bantuan.class);
            startActivity(navdirect);
        } else if (id == R.id.nav_tagihan) {
            Intent navdirect = new Intent(MainActivity.this, SurveyActivity.class);
            startActivity(navdirect);
        } else if (id == R.id.nav_profile) {
            Intent navdirect = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(navdirect);
        } else if (id == R.id.nav_dark_mode) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Buat time out session
    public static final long DISCONNECT_TIMEOUT = 5 * 60 * 1000; // 30 sec = 30 * 1000 ms
    private Handler disconnectHandler = new Handler() {
        public void handleMessage(Message msg) {
        }
    };

    private Runnable disconnectCallback = new Runnable() {
        @Override
        public void run() {

            // Perform any required operation on disconnect
            new MaterialStyledDialog.Builder(MainActivity.this)
                    .setTitle("Session Abis")
                    .setDescription("Anda akan keluar otomatis.\n" +
                            "Klik Ya untuk logout!")
                    .setIcon(R.drawable.question)
                    .setHeaderDrawable(R.drawable.bg_gradient_orange)
                    .withDialogAnimation(true)
                    .withIconAnimation(true)
                    .setCancelable(false)
                    .setPositiveText("Ya")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            endSession();
                        }
                    })
                    .show();
        }
    };

    public void resetDisconnectTimer() {
        disconnectHandler.removeCallbacks(disconnectCallback);
        disconnectHandler.postDelayed(disconnectCallback, DISCONNECT_TIMEOUT);
    }

    public void stopDisconnectTimer() {
        disconnectHandler.removeCallbacks(disconnectCallback);
    }

    @Override
    public void onUserInteraction() {
        resetDisconnectTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        resetDisconnectTimer();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopDisconnectTimer();
    }



    private void cekpassword(){
        email = sharedPrefManager.getSPEmail();
        mApiService.CekPassword(email).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call< ResponseBody > call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try{
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("success").equalsIgnoreCase("0")) {
                            //
                            String message = jsonRESULTS.getString("message");
                            editpassword(message);
                        }else{
                            //Toast.makeText(mContext, "Halo, harap ganti password secara berkala." ,Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                } else{
                    Toast.makeText(mContext, "Response Gagal Memanggil" ,Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure (Call<ResponseBody> call, Throwable t){
                Toast.makeText(mContext, "Tak Ada Koneksi Internet" ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editpassword(String message) {
        new MaterialStyledDialog.Builder(this)
                .setTitle("Akun kamu tidak aman")
                .setDescription(message)
                .setIcon(R.drawable.li_bantuan)
                .setHeaderDrawable(R.drawable.bg_gradient_orange)
                .withDialogAnimation(true)
                .withIconAnimation(true)
                .setCancelable(false)
                .setPositiveText("Ya")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startActivity(new Intent(mContext, EditProfileActivity.class));
                        overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
                    }
                })
                .setNegativeText("Kembali")
                .show();
    }
}