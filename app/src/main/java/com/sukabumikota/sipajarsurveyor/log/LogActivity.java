package com.sukabumikota.sipajarsurveyor.log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sukabumikota.sipajarsurveyor.MainActivity;
import com.sukabumikota.sipajarsurveyor.R;
import com.sukabumikota.sipajarsurveyor.adapter.LogAdapter;
import com.sukabumikota.sipajarsurveyor.apihelper.BaseApiService;
import com.sukabumikota.sipajarsurveyor.apihelper.koneksi;
import com.sukabumikota.sipajarsurveyor.auth.SharedPrafManager;
import com.sukabumikota.sipajarsurveyor.dialog.InternetDialogActivity;
import com.sukabumikota.sipajarsurveyor.model.ResponseLog;
import com.sukabumikota.sipajarsurveyor.model.SemuaLogItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogActivity extends AppCompatActivity {
    ProgressDialog loading;
    Context mContext;
    List<SemuaLogItem> semualogItemList = new ArrayList<>();
    LogAdapter logAdapter;
    BaseApiService mApiiService;
    RecyclerView recyclerView;
    String sv_id;
    SharedPrafManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        recyclerView = findViewById(R.id.rvlog);
        sharedPrefManager = new SharedPrafManager(this); // ini
        mContext = this;
        mApiiService = koneksi.getAPIService();
        logAdapter = new LogAdapter(this, semualogItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getResultListLog();
    }
    private void getResultListLog() {
        loading = ProgressDialog.show(this, null, "Harap Tunggu.....", true, false);
        sv_id = sharedPrefManager.getSPId();
        mApiiService.GetLog(sv_id).enqueue(new Callback<ResponseLog>() {
             @Override
             public void onResponse(Call<ResponseLog> call, Response<ResponseLog> response) {
                 if (response.isSuccessful()) {
                     loading.dismiss();
                     Boolean error = response.body().isError();
                     semualogItemList = response.body().getLog();
                     if(error.equals(true)){
                         String msg = "Tidak ada data !";
                         Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                     }else{

                         if(semualogItemList==null){
                             String msg = "Kesalahan Mengambil Data";
                             Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                         }else{
                             recyclerView.setAdapter(new LogAdapter(mContext, semualogItemList));
                             logAdapter.notifyDataSetChanged();
                         }

                     }
                 } else {
                     loading.dismiss();
                     Toast.makeText(mContext, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                 }
             }

             @Override
            public void onFailure(Call<ResponseLog> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                Intent noinet = new Intent(LogActivity.this, InternetDialogActivity.class);
                startActivity(noinet);
            }
        });
    }

    public void refresh(View view) {
        Intent i = new Intent(LogActivity.this, LogActivity.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(i);
        overridePendingTransition(0, 0);
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
}