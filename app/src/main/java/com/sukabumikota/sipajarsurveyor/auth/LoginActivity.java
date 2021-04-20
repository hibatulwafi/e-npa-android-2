package com.sukabumikota.sipajarsurveyor.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.sukabumikota.sipajarsurveyor.MainActivity;
import com.sukabumikota.sipajarsurveyor.R;
import com.sukabumikota.sipajarsurveyor.apihelper.BaseApiService;
import com.sukabumikota.sipajarsurveyor.apihelper.koneksi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class LoginActivity extends AppCompatActivity {
    EditText setEmail, setPassword;

    ProgressDialog loading;
    Context mContext;
    BaseApiService mApiService;
    SharedPrafManager sharedPrafManager;
    public static final String session_status = "session_status";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors

        setContentView(R.layout.activity_login);
        changeStatusBarColor();
        setEmail=findViewById(R.id.editTextEmail);
        setPassword=findViewById(R.id.editTextPassword);
        mContext = this;
        mApiService = koneksi.getAPIService();
        sharedPrafManager = new SharedPrafManager(this);
        if (sharedPrafManager.getSPSudahLogin()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onMulaiClick(View View){
        loading = ProgressDialog.show(mContext, null, "Tunggu yah..",true,false);
        //startActivity(new Intent(this, MainActivity.class));
        requestLogin();
        //overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    private void requestLogin(){
        mApiService.loginRequest(setEmail.getText().toString(),setPassword.getText().toString())
                .enqueue(new Callback<ResponseBody>(){
                    @Override
                    public void onResponse (Call<ResponseBody> call, Response<ResponseBody> response){
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try{
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")) {
                                    String nama_depan = jsonRESULTS.getJSONObject("logindata").getString("user_first_name");
                                    String nama_belakang = jsonRESULTS.getJSONObject("logindata").getString("user_last_name");
                                    String username = jsonRESULTS.getJSONObject("logindata").getString("user_email");
                                    String id = jsonRESULTS.getJSONObject("logindata").getString("id");


                                    onNothingData("Berhasil Login, Selamat datang "+nama_depan+ " "+nama_belakang);
                                    sharedPrafManager.saveSPString(sharedPrafManager.SP_NAMA, nama_depan);
                                    sharedPrafManager.saveSPString(sharedPrafManager.SP_NAMA_BELAKANG, nama_belakang);
                                    sharedPrafManager.saveSPString(sharedPrafManager.SP_EMAIL, username);
                                    sharedPrafManager.saveSPString(sharedPrafManager.SP_ID, id);
                                    sharedPrafManager.saveSPBoolean(sharedPrafManager.SP_SUDAH_LOGIN, true);
                                    Toast.makeText(LoginActivity.this,nama_depan+" "+nama_belakang,Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(mContext, MainActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();

                                }else{
                                    String error_message = jsonRESULTS.getString("message");
                                    onNothingData(error_message);

                                }

                            } catch (JSONException e){
                                e.printStackTrace();
                            } catch (IOException e){
                                e.printStackTrace();
                            }
                        } else{
                            loading.dismiss();
                        }
                    }
                    @Override
                    public void onFailure (Call<ResponseBody> call, Throwable t){
                        Log.e("debug","onFailure : ERROR >"+ t.toString());
                        loading.dismiss();
                    }
                });
    }
    @Override
    public void onBackPressed(){
        verifyExit();
    }

    private void onNothingData(String message) {
        new MaterialStyledDialog.Builder(mContext)
                .setTitle(message)
                .setDescription("Pastikan Username atau Password sudah benar.")
                .setIcon(R.drawable.applogo)
                .setHeaderDrawable(R.drawable.bg_gradient_orange)
                .withDialogAnimation(true)
                .withIconAnimation(true)
                .setCancelable(false)
                .setNegativeText("OK")
                .show();
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
                        finishAffinity();
                        System.exit(0);
                    }
                })
                .setNegativeText("Kembali")
                .show();
    }


}
