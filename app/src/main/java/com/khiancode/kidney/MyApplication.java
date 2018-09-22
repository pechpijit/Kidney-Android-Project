package com.khiancode.kidney;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        initFont();
        initRealm();
    }

    private void initFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Mitr.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    private void initRealm() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("app.kidney")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
