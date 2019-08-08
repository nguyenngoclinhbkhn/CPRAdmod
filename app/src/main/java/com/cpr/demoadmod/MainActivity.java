package com.cpr.demoadmod;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cpr.demoadmod.banner.Banner;
import com.cpr.demoadmod.dialog.DialogAd;
import com.cpr.demoadmod.interfaces.OnLoadListener;
import com.cpr.demoadmod.model.Application;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Application> list;
    private LinearLayout linearAdSum;
    private TextView txtAppName;
    private TextView txtAd;
    private Button btnInStall;
    private ImageView imgLogo;
    private int randomSum;
    private LinearLayout linearAd;
    private Handler handler;

    private DialogAd dialogAd;
    private Banner banner; // declare banner

    private Banner banner2; // declare banner 2 to create by code

    private LinearLayout linearRoot;

    private LinearLayout linearAdSumBottom;

    private Button btnPauseBanner;
    private Button btnResumeBanner;
    private Button btnDestroyBanner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearAdSum = findViewById(R.id.linearAdSum);
        btnInStall = findViewById(R.id.btnInStall);
        txtAd = findViewById(R.id.txtAd);
        txtAppName = findViewById(R.id.txtAppName);
        imgLogo = findViewById(R.id.imgLogo);
        linearAd = findViewById(R.id.linearAd);
        banner = findViewById(R.id.banner); // find id banner in XML
        linearAdSumBottom = findViewById(R.id.linearAdSum);
        handler = new Handler();
        btnPauseBanner = findViewById(R.id.btnPauseAd);
        btnResumeBanner = findViewById(R.id.btnResumeAd);
        btnDestroyBanner = findViewById(R.id.btnDestroyAd);
        btnPauseBanner.setOnClickListener(this);
        btnResumeBanner.setOnClickListener(this);
        btnDestroyBanner.setOnClickListener(this);

    }

    // show dialog advertises
    public void showDialog(View view) {
        dialogAd = new DialogAd(MainActivity.this);
        //call method loadData to load data
        dialogAd.loadData();

        //listener to listen when ad load succes, fail or close
        dialogAd.setOnLoadListener(new OnLoadListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void loadDataSuccess() {
                //after load data success
                dialogAd.showAd();
            }

            @Override
            public void loadDataFail() {
                //after load data first time fail
                Toast.makeText(MainActivity.this, "Load first fail", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void loadDataClose() {
                //when click close ad , data will be reload
                dialogAd.onDismissAd();
                Toast.makeText(MainActivity.this, "Ad close", Toast.LENGTH_SHORT).show();
            }

        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPauseAd: {
                //simulation activity is paused
                banner.onPause();
            }
            break;
            case R.id.btnResumeAd: {
                //simulation activity is resumed
                banner.onResume();
            }
            break;
            case R.id.btnDestroyAd: {
                //simulation activity is destroyed
                banner.onDestroy();
            }
            break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        banner.onDestroy();
    }
}
