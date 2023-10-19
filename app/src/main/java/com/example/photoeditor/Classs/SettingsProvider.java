package com.example.photoeditor.Classs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/* loaded from: classes3.dex */
public class SettingsProvider {
    private static final String PREF_PREMIUM = "pref_premium";
    private static final String PREF_USE_HIGH_QUALITY = "pref_high_quality";
    private static final String PREF_USE_WATERMARK = "pref_watermark";
    private Context mContext;

    public SettingsProvider(Context context) {
        this.mContext = context;
    }

    public void setPremium(boolean z) {
        getSharedPreferences(this.mContext).edit().putBoolean(PREF_PREMIUM, z).apply();
    }

    public boolean hasPremium() {
        return getSharedPreferences(this.mContext).getBoolean(PREF_PREMIUM, false);
    }

    public boolean useWatermark() {
        return getSharedPreferences(this.mContext).getBoolean(PREF_USE_WATERMARK, false);
    }

    public boolean useHighQuality() {
        return getSharedPreferences(this.mContext).getBoolean(PREF_USE_HIGH_QUALITY, false);
    }

    private SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
