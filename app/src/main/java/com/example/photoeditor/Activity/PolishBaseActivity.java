package com.example.photoeditor.Activity;

import androidx.appcompat.app.AppCompatActivity;

/* loaded from: classes7.dex */
public class PolishBaseActivity extends AppCompatActivity {
    public void isPermissionGranted(boolean z, String str) {
    }

    public void setFullScreen() {
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 52) {
            isPermissionGranted(iArr[0] == 0, strArr[0]);
        }
    }
}
