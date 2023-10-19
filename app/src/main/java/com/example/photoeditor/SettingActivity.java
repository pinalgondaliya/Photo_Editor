package com.example.photoeditor;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;

/* loaded from: classes8.dex */
public class SettingActivity extends AppCompatActivity {
    RelativeLayout animation_rl;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.animation_rl);
        this.animation_rl = relativeLayout;
        relativeLayout.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_down_to_up));
    }
}
