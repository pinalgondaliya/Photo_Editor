package com.example.photoeditor.Classs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.core.app.NotificationCompat;

import com.example.photoeditor.ModelClass.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class SessionHandler {
    private static final String PREF_LOGGED_IN = "pref_session_logged_in";
    private static final String PREF_PWD = "pref_session_pwd";
    private static final String PREF_USER = "pref_session_user";
    private static final SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private static String mSessionToken;
    private static SharedPreferences mSharedPreferenes;
    private static User mUser;

    /* loaded from: classes3.dex */
    public interface GetSessionCallback {
        void onTokenReceived(boolean z);
    }

    /* loaded from: classes3.dex */
    public interface LoginCallback {
        void onLoggedIn(User user, boolean z);
    }

    /* loaded from: classes3.dex */
    class C05773 implements LoginCallback {
        final LoginCallback val$callback;

        C05773(LoginCallback loginCallback) {
            this.val$callback = loginCallback;
        }

        @Override // com.example.photoareditor.Classs.SessionHandler.LoginCallback
        public void onLoggedIn(User user, boolean z) {
            this.val$callback.onLoggedIn(user, z);
        }
    }

    /* loaded from: classes3.dex */
    public class C06241 extends AsyncHttpResponseHandler {
        final GetSessionCallback val$callback;

        C06241(GetSessionCallback getSessionCallback) {
            this.val$callback = getSessionCallback;
        }

        @Override // com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            boolean z;
            try {
                String unused = SessionHandler.mSessionToken = new JSONObject(new String(bArr)).getJSONObject("data").getString("sessionId");
                z = true;
            } catch (Exception e) {
                z = false;
            }
            this.val$callback.onTokenReceived(z);
        }

        @Override // com.loopj.android.http.AsyncHttpResponseHandler
        public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
            this.val$callback.onTokenReceived(false);
        }
    }

    /* loaded from: classes3.dex */
    public class C06252 extends AsyncHttpResponseHandler {
        final LoginCallback val$callback;
        final String val$password;

        C06252(String str, LoginCallback loginCallback) {
            this.val$password = str;
            this.val$callback = loginCallback;
        }

        @Override // com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            try {
                JSONObject jSONObject = new JSONObject(new String(bArr)).getJSONObject("data");
                User user = new User();
                user.id = Long.valueOf(jSONObject.getLong("userId"));
                user.username = jSONObject.getString("username");
                user.email = jSONObject.getString(NotificationCompat.CATEGORY_EMAIL);
                user.token = jSONObject.getString("token");
                user.validUntil = SessionHandler.dateFormate.parse(jSONObject.getString("validUntil"));
                User unused = SessionHandler.mUser = user;
                SessionHandler.this.saveUsername(user.username);
                SessionHandler.this.savePassword(this.val$password);
                SessionHandler.this.saveLoggedIn(true);
            } catch (ParseException | JSONException e) {
            }
            this.val$callback.onLoggedIn(SessionHandler.mUser, false);
        }

        @Override // com.loopj.android.http.AsyncHttpResponseHandler
        public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
            User unused = SessionHandler.mUser = null;
            this.val$callback.onLoggedIn(null, true);
        }
    }

    public SessionHandler(Context context) {
        mSharedPreferenes = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void getSessionToken(GetSessionCallback getSessionCallback) {
        ServiceApi.getSessionTokenV1(new C06241(getSessionCallback));
    }

    public void login(String str, String str2, LoginCallback loginCallback) {
        ServiceApi.authenticate(str, str2, new C06252(str2, loginCallback));
    }

    public void logout() {
        mUser = null;
        saveLoggedIn(false);
        saveUsername("BuildConfig.VERSION_NAME");
        savePassword("BuildConfig.VERSION_NAME");
    }

    public void restoreSession(LoginCallback loginCallback) {
        login(readStoredUsername(), readStoredPassword(), new C05773(loginCallback));
    }

    public String getSessionToken() {
        return mSessionToken;
    }

    public User getUser() {
        return mUser;
    }

    public boolean isLoggedIn() {
        return readLoggedIn();
    }

    private String readStoredUsername() {
        return mSharedPreferenes.getString(PREF_USER, "BuildConfig.VERSION_NAME");
    }

    private String readStoredPassword() {
        return mSharedPreferenes.getString(PREF_PWD, "BuildConfig.VERSION_NAME");
    }

    private boolean readLoggedIn() {
        return mSharedPreferenes.getBoolean(PREF_LOGGED_IN, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveLoggedIn(boolean z) {
        mSharedPreferenes.edit().putBoolean(PREF_LOGGED_IN, z).apply();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveUsername(String str) {
        mSharedPreferenes.edit().putString(PREF_USER, str).apply();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void savePassword(String str) {
        mSharedPreferenes.edit().putString(PREF_PWD, str).apply();
    }
}
