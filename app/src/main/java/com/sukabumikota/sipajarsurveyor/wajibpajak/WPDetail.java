package com.sukabumikota.sipajarsurveyor.wajibpajak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sukabumikota.sipajarsurveyor.MainActivity;
import com.sukabumikota.sipajarsurveyor.R;
import com.sukabumikota.sipajarsurveyor.adapter.ObjekPajakAdapter;
import com.sukabumikota.sipajarsurveyor.apihelper.BaseApiService;
import com.sukabumikota.sipajarsurveyor.apihelper.koneksi;
import com.sukabumikota.sipajarsurveyor.model.ResponseObjekPajak;
import com.sukabumikota.sipajarsurveyor.model.SemuaObjekPajakItem;
import com.sukabumikota.sipajarsurveyor.model.SemuaWajibPajakItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WPDetail extends AppCompatActivity {
    TextView txtprofilenama,txtprofilnpwpd,txtprofileemail,txtprofilealamat,txtprofilenotelp,txtprofileusername,txtprofiletgldaftar;
    public static final String EXTRA_DATA= "extra_data";
    //Menampilkan titik sumur / objek pajak
    ProgressDialog loading;
    Context mContext;
    List<SemuaObjekPajakItem> semuaobjekpajakItemList = new ArrayList<>();
    ObjekPajakAdapter objekpajakAdapter;
    BaseApiService mApiiService;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w_p_detail);
        txtprofilenama = findViewById(R.id.txtprofilenama);
        txtprofilnpwpd = findViewById(R.id.txtprofilnpwpd);
        txtprofileemail = findViewById(R.id.txtprofileemail);
        txtprofilealamat = findViewById(R.id.txtprofilealamat);
        txtprofilenotelp = findViewById(R.id.txtprofilenotelp);
        txtprofileusername = findViewById(R.id.txtprofileusername);
        txtprofiletgldaftar = findViewById(R.id.txtprofiletgldaftar);
        SemuaWajibPajakItem semuawajibpajakitem = getIntent().getParcelableExtra(EXTRA_DATA);

        txtprofilenama.setText(semuawajibpajakitem.getNama());
        txtprofilnpwpd.setText(semuawajibpajakitem.getNpwpd());
        txtprofileemail.setText(semuawajibpajakitem.getEmail());
        txtprofilealamat.setText(semuawajibpajakitem.getAlamat());
        txtprofilenotelp.setText(semuawajibpajakitem.getTelp());
        txtprofileusername.setText(semuawajibpajakitem.getEmail());
        txtprofiletgldaftar.setText("Terdaftar Sejak : "+semuawajibpajakitem.getTanggal_daftar());
        // Menampilkan Objek Pajak

        recyclerView = findViewById(R.id.rvop);
        mContext = this;
        mApiiService = koneksi.getAPIService();
        objekpajakAdapter = new ObjekPajakAdapter(this, semuaobjekpajakItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        String wp_id = semuawajibpajakitem.getWp_id();
        getResultListObjekPajak(wp_id);
    }

    private void getResultListObjekPajak(String wp_id) {
        loading = ProgressDialog.show(this, null, "Harap Tunggu.....", true, false);

        mApiiService.getObjekPajak(wp_id).enqueue(new Callback<ResponseObjekPajak>() {
            @Override
            public void onResponse(Call<ResponseObjekPajak> call, Response<ResponseObjekPajak> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    Boolean error = response.body().isError();
                    semuaobjekpajakItemList = response.body().getObjekpajak();
                    if(error.equals(true)){
                        String msg = "Tidak ada data !";
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    }else{
                        if(semuaobjekpajakItemList==null){
                            String msg = "Kesalahan Mengambil Data";
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                        }else{
                            recyclerView.setAdapter(new ObjekPajakAdapter(mContext, semuaobjekpajakItemList));
                            objekpajakAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseObjekPajak> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBackClick(View view) {
        startActivity(new Intent(this, WPActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, WPActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }
}
