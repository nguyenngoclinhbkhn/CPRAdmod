package com.cpr.demoadmod.dialog;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.cpr.demoadmod.R;
import com.cpr.demoadmod.interfaces.OnLoadListener;
import com.cpr.demoadmod.model.Application;
import com.cpr.demoadmod.retrofit.Data;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogAd extends Dialog implements View.OnClickListener {
    public Activity activity;
    private ImageView btnNext;
    private ImageView btnPrevious;
    private ImageView imgCover;
    private Button btnInstall;
    private TextView txtAd;
    private ImageButton imgCancel;
    private ImageView imgLogo;
    private LinearLayout linearSum;
    private List<Application> list;
    private int random;
    private Handler handler;
    private OnLoadListener onLoadListener;
    private static final String DATA = "Data";
    private int sum;
    private SharedPreferences preferencesSum;

    private String[] arrayKey = {"AppId", "AppName", "AppPackage", "AppLogo",
            "BgStartColor", "BgEndColor", "BgBtnUnclicked", "BgBtnClicked", "ColorTextTitle",
            "ColorTextButton", "Cover"};

    public DialogAd(Activity activity) {
        super(activity);
        this.activity = activity;
        list = new ArrayList<>();
        preferencesSum = activity.getSharedPreferences("Sum", Context.MODE_PRIVATE);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ad);
        initView();
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
        imgCancel.setOnClickListener(this);


    }

    private void initView() {
        linearSum = findViewById(R.id.linearSum);
        btnPrevious = findViewById(R.id.imgActionPrevious);
        btnNext = findViewById(R.id.imgActionNext);
        imgCover = findViewById(R.id.imgCover);
        imgLogo = findViewById(R.id.imgLogo);
        imgCancel = findViewById(R.id.imgButtonCancel);
        txtAd = findViewById(R.id.txtAdDialog);
        btnInstall = findViewById(R.id.btnInstallDialog);
        handler = new Handler();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgActionNext: {
                Toast.makeText(activity, "Next", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.imgActionPrevious: {
                Toast.makeText(activity, "previous", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.imgButtonCancel: {
                onLoadListener.loadDataClose();
            }
            break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showAd(int randomSum) {
        String packageName = list.get(randomSum).getPackageName();
        String logo = list.get(randomSum).getLogo();
        String appName = list.get(randomSum).getName();
        String bgStart = list.get(randomSum).getBgStartColor();
        String bgEnd = list.get(randomSum).getBgEndColor();
        String bgBtnClicked = list.get(randomSum).getBgBtnClickedColor();
        String bgBtnUnclicked = list.get(randomSum).getBgBtnUnclickedColor();
        String colorTitle = list.get(randomSum).getColorTitle();
        String colorTextButton = list.get(randomSum).getColorTextButton();
        String cover = list.get(randomSum).getCover();
        createAd(linearSum, imgLogo, txtAd, imgCover, btnInstall,
                btnPrevious, btnNext, logo, appName, packageName, bgStart, bgEnd,
                bgBtnUnclicked, bgBtnClicked, colorTitle, colorTextButton, cover);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void createAd(LinearLayout linearAd, ImageView imgLogo, TextView txtAd, ImageView imgCover,
                         final Button btnInStall, ImageView imgPrevious, ImageView imgNext,
                         String logo, String nameApp, final String packageName,
                         final String colorStartBG, final String colorEndBG,
                         final String colorStartBtn, final String colorEndBtn,
                         String colorTextButton, String colorTitle, String cover) {
        GradientDrawable bgGradient = bgGradient(colorStartBG, colorEndBG);

        Drawable drawable = activity.getDrawable(R.drawable.bg_install);
        final GradientDrawable drawable1 = (GradientDrawable) drawable;
        drawable1.setStroke(convertDpToPixel(1, activity), Color.parseColor(colorTitle));
        drawable1.setColor(Color.parseColor(colorStartBG));
        Log.e("TAG", "btnInstall " + btnInStall);
        btnInStall.setBackground(drawable1);

        linearAd.setBackground(bgGradient);
        Glide.with(activity).load(logo).into(imgLogo);

        Glide.with(activity).load(cover).into(imgCover);
        btnInStall.setTextColor(Color.parseColor(colorTextButton));
        txtAd.setTextColor(Color.parseColor(colorTextButton));
        Glide.with(activity).load(cover).into(imgCover);
        btnInStall.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        btnInStall.setBackgroundColor(Color.parseColor(colorEndBtn));

                    }
                    break;
                    case MotionEvent.ACTION_UP: {
                        btnInStall.setBackground(drawable1);
                        Uri uri = Uri.parse("market://details?id=" + packageName);
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            activity.startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
                        }
                    }
                }
                return false;
            }
        });


    }

    private GradientDrawable bgGradient(String colorStart, String colorEnd) {
        int[] colors = {Color.parseColor(colorStart), Color.parseColor(colorEnd)};
        GradientDrawable gD = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
        gD.setCornerRadius(0f);
        return gD;
    }

    private static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    public void loadData() {
        Call<JsonElement> call = Data.getListApp().listApp();
        call.enqueue(new Callback<JsonElement>() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                list.clear();
                JsonElement jsonElement = response.body();
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonArray jsonArray = jsonObject.getAsJsonArray("data");
                if (jsonArray.size() > 0) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        SharedPreferences preferences = activity.getSharedPreferences(DATA + i, Context.MODE_PRIVATE);
                        JsonObject jsonObject1 = jsonArray.get(i).getAsJsonObject();
                        int jsonId = jsonObject1.get("id").getAsInt();
                        preferences.edit().putString(arrayKey[0], String.valueOf(jsonId)).commit();
                        String appName = jsonObject1.get("name").getAsString();
                        preferences.edit().putString(arrayKey[1], appName).commit();
                        String jsonPackage = jsonObject1.get("package").getAsString();
                        preferences.edit().putString(arrayKey[2], jsonPackage).commit();
                        String logoApp = jsonObject1.get("logo").getAsString();
                        preferences.edit().putString(arrayKey[3], logoApp).commit();
                        String bgStartColor = jsonObject1.get("bg_start_color").getAsString();
                        preferences.edit().putString(arrayKey[4], bgStartColor).commit();
                        String bgEndColor = jsonObject1.get("bg_end_color").getAsString();
                        preferences.edit().putString(arrayKey[5], bgEndColor).commit();
                        String bgBtnUnclicked = jsonObject1.get("btn_unclicked_color").getAsString();
                        preferences.edit().putString(arrayKey[6], bgBtnUnclicked).commit();
                        String bgBtnClicked = jsonObject1.get("btn_clicked_color").getAsString();
                        preferences.edit().putString(arrayKey[7], bgBtnClicked).commit();
                        String colorTextTitle = jsonObject1.get("text_title_color").getAsString();
                        preferences.edit().putString(arrayKey[8], colorTextTitle).commit();
                        String colorTextButton = jsonObject1.get("text_intall_color").getAsString();
                        preferences.edit().putString(arrayKey[9], colorTextButton).commit();
                        String cover = jsonObject1.get("cover").getAsString();
                        preferences.edit().putString(arrayKey[10], cover).commit();
                    }
                }
                sum = jsonArray.size();
                if (sum > 0) {
                    preferencesSum.edit().putString("sum", String.valueOf(sum)).commit();
                    for (int i = 0; i < sum; i++) {
                        SharedPreferences preferences = activity.getSharedPreferences(DATA + i, Context.MODE_PRIVATE);
                        int id = Integer.parseInt(preferences.getString(arrayKey[0], "0"));
                        String appName = preferences.getString(arrayKey[1], "");
                        String jsonPackage = preferences.getString(arrayKey[2], "");
                        String logoApp = preferences.getString(arrayKey[3], "");
                        String bgStartColor = preferences.getString(arrayKey[4], "");
                        String bgEndColor = preferences.getString(arrayKey[5], "");
                        String bgBtnUnclicked = preferences.getString(arrayKey[6], "");
                        String bgBtnClicked = preferences.getString(arrayKey[7], "");
                        String colorTextTitle = preferences.getString(arrayKey[8], "");
                        String colorTextButton = preferences.getString(arrayKey[9], "");
                        String cover = preferences.getString(arrayKey[10], "");
                        list.add(new Application(id, appName, jsonPackage,
                                logoApp, bgStartColor, bgEndColor, bgBtnUnclicked, bgBtnClicked,
                                colorTextTitle, colorTextButton, cover));
                    }
                    Log.e("TAG", "Show ad online");
                    onLoadListener.loadDataSuccess();
                } else {
                    onLoadListener.loadDataFail();
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                int sum = Integer.parseInt(preferencesSum.getString("sum", "0"));
                if (sum > 0) {
                    for (int i = 0; i < sum; i++) {
                        SharedPreferences preferences = activity.getSharedPreferences(DATA + i, Context.MODE_PRIVATE);
                        int id = Integer.parseInt(preferences.getString(arrayKey[0], "0"));
                        String appName = preferences.getString(arrayKey[1], "");
                        String jsonPackage = preferences.getString(arrayKey[2], "");
                        String logoApp = preferences.getString(arrayKey[3], "");
                        String bgStartColor = preferences.getString(arrayKey[4], "");
                        String bgEndColor = preferences.getString(arrayKey[5], "");
                        String bgBtnUnclicked = preferences.getString(arrayKey[6], "");
                        String bgBtnClicked = preferences.getString(arrayKey[7], "");
                        String colorTextTitle = preferences.getString(arrayKey[8], "");
                        String colorTextButton = preferences.getString(arrayKey[9], "");
                        String cover = preferences.getString(arrayKey[10], "");
                        list.add(new Application(id, appName, jsonPackage,
                                logoApp, bgStartColor, bgEndColor, bgBtnUnclicked, bgBtnClicked,
                                colorTextTitle, colorTextButton, cover));
                        Log.e("TAG", "name " + appName);
                    }
                    Log.e("TAG", "Show ad offline");
                    onLoadListener.loadDataSuccess();
                } else {
                    onLoadListener.loadDataFail();
                }
            }
        });
    }

    private Runnable runnable = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            if (random == 0) {
                btnPrevious.setVisibility(View.INVISIBLE);
            }
            showAd(random);
            random++;
            if (random >= 3) {
                btnNext.setVisibility(View.INVISIBLE);
            }
            if (random >= 3) {
                random = 0;
            }
            handler.postDelayed(this, 3000);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showAd() {
        show();
        showAd(0);
        btnPrevious.setVisibility(View.INVISIBLE);
        handler.postDelayed(runnable, 3000);

    }

    private void reloadData(){
        Call<JsonElement> call = Data.getListApp().listApp();
        call.enqueue(new Callback<JsonElement>() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                list.clear();
                JsonElement jsonElement = response.body();
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonArray jsonArray = jsonObject.getAsJsonArray("data");
                if (jsonArray.size() > 0) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        SharedPreferences preferences = activity.getSharedPreferences(DATA + i, Context.MODE_PRIVATE);
                        JsonObject jsonObject1 = jsonArray.get(i).getAsJsonObject();
                        int jsonId = jsonObject1.get("id").getAsInt();
                        preferences.edit().putString(arrayKey[0], String.valueOf(jsonId)).commit();
                        String appName = jsonObject1.get("name").getAsString();
                        preferences.edit().putString(arrayKey[1], appName).commit();
                        String jsonPackage = jsonObject1.get("package").getAsString();
                        preferences.edit().putString(arrayKey[2], jsonPackage).commit();
                        String logoApp = jsonObject1.get("logo").getAsString();
                        preferences.edit().putString(arrayKey[3], logoApp).commit();
                        String bgStartColor = jsonObject1.get("bg_start_color").getAsString();
                        preferences.edit().putString(arrayKey[4], bgStartColor).commit();
                        String bgEndColor = jsonObject1.get("bg_end_color").getAsString();
                        preferences.edit().putString(arrayKey[5], bgEndColor).commit();
                        String bgBtnUnclicked = jsonObject1.get("btn_unclicked_color").getAsString();
                        preferences.edit().putString(arrayKey[6], bgBtnUnclicked).commit();
                        String bgBtnClicked = jsonObject1.get("btn_clicked_color").getAsString();
                        preferences.edit().putString(arrayKey[7], bgBtnClicked).commit();
                        String colorTextTitle = jsonObject1.get("text_title_color").getAsString();
                        preferences.edit().putString(arrayKey[8], colorTextTitle).commit();
                        String colorTextButton = jsonObject1.get("text_intall_color").getAsString();
                        preferences.edit().putString(arrayKey[9], colorTextButton).commit();
                        String cover = jsonObject1.get("cover").getAsString();
                        preferences.edit().putString(arrayKey[10], cover).commit();
                    }
                }
                sum = jsonArray.size();
                if (sum > 0) {
                    preferencesSum.edit().putString("sum", String.valueOf(sum)).commit();
                    for (int i = 0; i < sum; i++) {
                        SharedPreferences preferences = activity.getSharedPreferences(DATA + i, Context.MODE_PRIVATE);
                        int id = Integer.parseInt(preferences.getString(arrayKey[0], "0"));
                        String appName = preferences.getString(arrayKey[1], "");
                        String jsonPackage = preferences.getString(arrayKey[2], "");
                        String logoApp = preferences.getString(arrayKey[3], "");
                        String bgStartColor = preferences.getString(arrayKey[4], "");
                        String bgEndColor = preferences.getString(arrayKey[5], "");
                        String bgBtnUnclicked = preferences.getString(arrayKey[6], "");
                        String bgBtnClicked = preferences.getString(arrayKey[7], "");
                        String colorTextTitle = preferences.getString(arrayKey[8], "");
                        String colorTextButton = preferences.getString(arrayKey[9], "");
                        String cover = preferences.getString(arrayKey[10], "");
                        list.add(new Application(id, appName, jsonPackage,
                                logoApp, bgStartColor, bgEndColor, bgBtnUnclicked, bgBtnClicked,
                                colorTextTitle, colorTextButton, cover));
                    }
                    Log.e("TAG", "Reload new data ok");
                } else {
                    Log.e("TAG", "Reload new data not ok");
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("TAG", "Reload new data not ok");
            }
        });
    }

    public void onDismissAd() {
        dismiss();
        Log.e("TAG", "Reload data");
        reloadData();
        onLoadListener.loadDataClose();
        handler.removeCallbacks(runnable);
    }


}
