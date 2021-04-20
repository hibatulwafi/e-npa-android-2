package com.sukabumikota.sipajarsurveyor.objekpajak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.sukabumikota.sipajarsurveyor.R;
import com.sukabumikota.sipajarsurveyor.adapter.TabAdapter;
import com.sukabumikota.sipajarsurveyor.model.SemuaObjekPajakItem;

public class OPDetail extends AppCompatActivity {
    TextView txtnamaop,txttanggalregis,txtopalamat,txtnoipat,txtnamawp;
    public static final String EXTRA_DATA= "extra_data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_p_detail);

        txtnamaop = findViewById(R.id.txtnamaop);
        txttanggalregis = findViewById(R.id.txttanggalregis);

        SemuaObjekPajakItem semuaobjekpajakitem = getIntent().getParcelableExtra(EXTRA_DATA);

        txtnamaop.setText(semuaobjekpajakitem.getOp_nama());
        txttanggalregis.setText(semuaobjekpajakitem.getTanggal_registrasi());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Detail"));
        tabLayout.addTab(tabLayout.newTab().setText("Riwayat"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager =(ViewPager)findViewById(R.id.view_pager);

        TabAdapter tabsAdapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}