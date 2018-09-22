package com.khiancode.kidney;

import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.khiancode.kidney.helper.PrefUtils;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashActivity extends AwesomeSplash {

    @Override
    public void initSplash(ConfigSplash configSplash) {

        configSplash.setBackgroundColor(R.color.colorPrimary);
        configSplash.setAnimCircularRevealDuration(500);
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM);

        configSplash.setLogoSplash(R.drawable.rinones_logo);
        configSplash.setAnimLogoSplashDuration(1000);
        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce);

        configSplash.setTitleSplash("HUGTAI ฮักไต");
        configSplash.setTitleTextColor(R.color.white);
        configSplash.setTitleTextSize(45f);
        configSplash.setAnimTitleDuration(1300);
        configSplash.setAnimTitleTechnique(Techniques.FadeIn);
        configSplash.setTitleFont("fonts/Mitr.ttf");

    }

    @Override
    public void animationsFinished() {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (new PrefUtils(SplashActivity.this).getLoginStatus()) {
                            startActivity(new Intent(SplashActivity.this,MainActivity.class));
                            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                            finish();
                        }else {
                            startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                            finish();
                        }
                    }
                }, 700);

    }

}