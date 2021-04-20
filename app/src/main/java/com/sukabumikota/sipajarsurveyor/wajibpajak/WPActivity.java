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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.sukabumikota.sipajarsurveyor.MainActivity;
import com.sukabumikota.sipajarsurveyor.R;
import com.sukabumikota.sipajarsurveyor.adapter.WajibPajakAdapter;
import com.sukabumikota.sipajarsurveyor.apihelper.BaseApiService;
import com.sukabumikota.sipajarsurveyor.apihelper.koneksi;
import com.sukabumikota.sipajarsurveyor.dialog.InternetDialogActivity;
import com.sukabumikota.sipajarsurveyor.model.ResponseWajibPajak;
import com.sukabumikota.sipajarsurveyor.model.SemuaWajibPajakItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WPActivity extends AppCompatActivity {
    ProgressDialog loading;
    Context mContext;
    List<SemuaWajibPajakItem> semuawajibpajakItemList = new ArrayList<>();
    WajibPajakAdapter wajibpajakAdapter;
    BaseApiService mApiiService;
    RecyclerView recyclerView;
    //Buat Pencarian
    private ImageButton SearchBtn;
    private EditText SearchField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w_p);
        recyclerView = findViewById(R.id.rvwp);
        //Buat Pencarian
        SearchBtn = findViewById(R.id.search_btn);
        SearchField = findViewById(R.id.search_field);
        mContext = this;
        mApiiService = koneksi.getAPIService();
        wajibpajakAdapter = new WajibPajakAdapter(this, semuawajibpajakItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getResultListWajibPajak();
        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = SearchField.getText().toString();
                CariData(searchText);

            }
        });

    }

    private void getResultListWajibPajak() {
        loading = ProgressDialog.show(this, null, "Harap Tunggu.....", true, false);

        mApiiService.getWajibPajak().enqueue(new Callback<ResponseWajibPajak>() {
            @Override
            public void onResponse(Call<ResponseWajibPajak> call, Response<ResponseWajibPajak> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    Boolean error = response.body().isError();
                    semuawajibpajakItemList = response.body().getWajibpajak();
                    if(error.equals(true)){
                        String msg = "Tidak ada data tagihan!";
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    }else{
                        if(semuawajibpajakItemList==null){
                            String msg = "Kesalahan Mengambil Data";
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                        }else{
                            recyclerView.setAdapter(new WajibPajakAdapter(mContext, semuawajibpajakItemList));
                            wajibpajakAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseWajibPajak> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                Intent noinet = new Intent(WPActivity.this, InternetDialogActivity.class);
                startActivity(noinet);
            }
        });
    }

    private void CariData(String searchText) {
        loading = ProgressDialog.show(this, null, "Harap Tunggu.....", true, false);
        mApiiService.cariWajibPajak(searchText).enqueue(new Callback<ResponseWajibPajak>() {
            @Override
            public void onResponse(Call<ResponseWajibPajak> call, Response<ResponseWajibPajak> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    Boolean error = response.body().isError();
                    semuawajibpajakItemList = response.body().getWajibpajak();
                    if(error.equals(true)){
                        String msg = "Tidak ada data !";
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    }else{
                        if(semuawajibpajakItemList==null){
                            String msg = "Kesalahan Mengambil Data";
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                        }else{
                            recyclerView.setAdapter(new WajibPajakAdapter(mContext, semuawajibpajakItemList));
                            wajibpajakAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseWajibPajak> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                Intent noinet = new Intent(WPActivity.this, InternetDialogActivity.class);
                startActivity(noinet);
            }
        });
    }


    public void onBackClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }
    public void refresh(View view) {
        Intent i = new Intent(WPActivity.this, WPActivity.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(i);
        overridePendingTransition(0, 0);
    }
}