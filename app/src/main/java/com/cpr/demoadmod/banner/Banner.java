package com.cpr.demoadmod.banner;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cpr.demoadmod.R;
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

public class Banner extends LinearLayout {
    private LayoutInflater inflater;
    private Context context;
    private ImageView imgLogo;
    private TextView txtApp;
    private TextView txtAd;
    private Button btnInstall;
    private LinearLayout linearAdSum;
    private Handler handler;
    private List<Application> list;
    private int randomSum;
    private int sum;
    private static String BANNER = "BANNER";
    private String[] arrayKey = {"AppId", "AppName", "AppPackage", "AppLogo",
            "BgStartColor", "BgEndColor", "BgBtnUnclicked", "BgBtnClicked", "ColorTextTitle",
            "ColorTextButton", "Cover"};
    private SharedPreferences preferencesSum;

    public Banner(Context context) {
        super(context);
        init(context);
    }

    public Banner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Banner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(final Context context) {
        this.context = context;
        preferencesSum = context.getSharedPreferences("BannerSum", Context.MODE_PRIVATE);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ad_banner, null);
        txtAd = view.findViewById(R.id.txtAd);
        txtApp = view.findViewById(R.id.txtAppName);
        btnInstall = view.findViewById(R.id.btnInStall);
        imgLogo = view.findViewById(R.id.imgLogo);
        linearAdSum = view.findViewById(R.id.linearAd);
        handler = new Handler();
        Call<JsonElement> call = Data.getListApp().listApp();
        list = new ArrayList<>();
        call.enqueue(new Callback<JsonElement>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                list.clear();
                JsonElement jsonElement = response.body();
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonArray jsonArray = jsonObject.getAsJsonArray("data");
                if (jsonArray.size() > 0) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        SharedPreferences preferencesBanner = context.getSharedPreferences(BANNER + i, Context.MODE_PRIVATE);
                        JsonObject jsonObject1 = jsonArray.get(i).getAsJsonObject();
                        int jsonId = jsonObject1.get("id").getAsInt();
                        preferencesBanner.edit().putString(arrayKey[0], String.valueOf(jsonId)).commit();
                        String appName = jsonObject1.get("name").getAsString();
                        preferencesBanner.edit().putString(arrayKey[1], appName).commit();
                        String jsonPackage = jsonObject1.get("package").getAsString();
                        preferencesBanner.edit().putString(arrayKey[2], jsonPackage).commit();
                        String logoApp = jsonObject1.get("logo").getAsString();
                        preferencesBanner.edit().putString(arrayKey[3], logoApp).commit();
                        String bgStartColor = jsonObject1.get("bg_start_color").getAsString();
                        preferencesBanner.edit().putString(arrayKey[4], bgStartColor).commit();
                        String bgEndColor = jsonObject1.get("bg_end_color").getAsString();
                        preferencesBanner.edit().putString(arrayKey[5], bgEndColor).commit();
                        String bgBtnUnclicked = jsonObject1.get("btn_unclicked_color").getAsString();
                        preferencesBanner.edit().putString(arrayKey[6], bgBtnUnclicked).commit();
                        String bgBtnClicked = jsonObject1.get("btn_clicked_color").getAsString();
                        preferencesBanner.edit().putString(arrayKey[7], bgBtnClicked).commit();
                        String colorTextTitle = jsonObject1.get("text_title_color").getAsString();
                        preferencesBanner.edit().putString(arrayKey[8], colorTextTitle).commit();
                        String colorTextButton = jsonObject1.get("text_intall_color").getAsString();
                        preferencesBanner.edit().putString(arrayKey[9], colorTextButton).commit();
                        String cover = jsonObject1.get("cover").getAsString();
                        preferencesBanner.edit().putString(arrayKey[10], cover).commit();
                    }
                }
                sum = jsonArray.size();
                if (sum > 0) {
                    preferencesSum.edit().putString("Banner", String.valueOf(sum)).commit();
                }
                loadData();

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                loadData();
            }
        });
        addView(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void loadData() {
        int sum = Integer.parseInt(preferencesSum.getString("Banner", "0"));
        if (sum > 0) {
            for (int i = 0; i < sum; i++) {
                SharedPreferences preferences = context.getSharedPreferences(BANNER + i, Context.MODE_PRIVATE);
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
        }
        showAd(0, context);
        handler.postDelayed(runnable, 500);
    }


    private Runnable runnable = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            showAd(randomSum, context);
            randomSum++;
            if (randomSum >= 3) {
                randomSum = 0;
            }
            handler.postDelayed(this, 500);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showAd(int randomSum, Context context) {
        linearAdSum.setVisibility(View.VISIBLE);
        String packageName = list.get(randomSum).getPackageName();
        String logo = list.get(randomSum).getLogo();
        String appName = list.get(randomSum).getName();
        String bgStart = list.get(randomSum).getBgStartColor();
        String bgEnd = list.get(randomSum).getBgEndColor();
        String bgBtnClicked = list.get(randomSum).getBgBtnClickedColor();
        String bgBtnUnclicked = list.get(randomSum).getBgBtnUnclickedColor();
        String colorTitle = list.get(randomSum).getColorTitle();
        String colorTextButton = list.get(randomSum).getColorTextButton();
        createAd(context, linearAdSum, txtApp, imgLogo, txtAd, btnInstall, logo, appName, packageName, bgStart, bgEnd,
                bgBtnUnclicked, bgBtnClicked, colorTitle, colorTextButton);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createAd(final Context context, LinearLayout linearAd, TextView txtAppName, ImageView imgLogo, TextView txtAd,
                          final Button btnInStall, String logo, String nameApp, final String packageName,
                          final String colorStartBG, final String colorEndBG,
                          final String colorStartBtn, final String colorEndBtn,
                          String colorTextButton, String colorTitle) {
        GradientDrawable bgGradient = bgGradient(colorStartBG, colorEndBG);

        Drawable drawable = context.getDrawable(R.drawable.bg_install);
        final GradientDrawable drawable1 = (GradientDrawable) drawable;
        drawable1.setStroke(convertDpToPixel(1, context), Color.parseColor(colorTitle));
        drawable1.setColor(Color.parseColor(colorStartBG));
        btnInStall.setBackground(drawable1);

        linearAd.setBackground(bgGradient);
        txtAppName.setText(nameApp);
        Glide.with(context).load(logo).into(imgLogo);
//        btnInStall.setBackgroundColor(Color.parseColor(colorStartBtn));
        btnInStall.setTextColor(Color.parseColor(colorTextButton));
        txtAppName.setTextColor(Color.parseColor(colorTitle));
        txtAd.setTextColor(Color.parseColor(colorTextButton));
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
                            context.startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            context.startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
                        }
                    }
                }
                return false;
            }
        });


    }

    private static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }


    private GradientDrawable bgGradient(String colorStart, String colorEnd) {
        int[] colors = {Color.parseColor(colorStart), Color.parseColor(colorEnd)};
        GradientDrawable gD = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
        gD.setCornerRadius(0f);
        return gD;
    }

    public void onPause() {
        handler.removeCallbacks(runnable);
    }

    public void onResume() {
        handler.postDelayed(runnable, 500);
    }

    public void onDestroy() {
        handler.removeCallbacks(runnable);
        randomSum = 0;
    }

}
