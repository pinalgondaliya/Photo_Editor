package com.example.photoeditor;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;

/* loaded from: classes8.dex */
public class BackdropViewAnimation {
    private static String TAG = "BackdropViewAnimation";
    private View backdrop;
    private boolean backdropShown;
    private View buttonView;
    private Integer closeIcon;
    private Integer colorIcon;
    private Context context;
    private int height;
    private Integer openIcon;
    private View sheet;
    private StateListener stateListener;
    private final AnimatorSet animatorSet = new AnimatorSet();
    private Interpolator interpolator = new AccelerateDecelerateInterpolator();
    private DisplayMetrics displayMetrics = new DisplayMetrics();

    /* loaded from: classes8.dex */
    public interface StateListener {
        void onClose(ObjectAnimator objectAnimator);

        void onOpen(ObjectAnimator objectAnimator);
    }

    public void addStateListener(StateListener stateListener2) {
        this.stateListener = stateListener2;
    }

    public BackdropViewAnimation(Context context2, View view, View view2) {
        this.context = context2;
        this.backdrop = view;
        this.sheet = view2;
        ((Activity) context2).getWindowManager().getDefaultDisplay().getMetrics(this.displayMetrics);
        this.height = this.displayMetrics.heightPixels;
    }

    public BackdropViewAnimation(Context context2, View view, View view2, Integer num, Integer num2, Integer num3) {
        this.context = context2;
        this.backdrop = view;
        this.sheet = view2;
        this.openIcon = num;
        this.closeIcon = num2;
        this.colorIcon = num3;
        ((Activity) context2).getWindowManager().getDefaultDisplay().getMetrics(this.displayMetrics);
        this.height = this.displayMetrics.heightPixels;
    }

    public ObjectAnimator toggle() {
        return toggle(null);
    }

    public ObjectAnimator toggle(View view) {
        this.backdropShown = !this.backdropShown;
        if (view != null) {
            this.buttonView = view;
        }
        View view2 = this.buttonView;
        if (view2 != null) {
            updateIcon(view2);
        }
        this.animatorSet.removeAllListeners();
        this.animatorSet.end();
        this.animatorSet.cancel();
        int bottom = this.backdrop.getBottom() - this.sheet.getTop();
        if (this.backdrop.getBottom() + this.sheet.getTop() > this.height && getActionBarSize() > 0) {
            bottom = (this.height - this.sheet.getTop()) - ((getActionBarSize() * 4) / 3);
        }
        View view3 = this.sheet;
        float[] fArr = new float[1];
        fArr[0] = this.backdropShown ? bottom : 0.0f;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view3, "translationY", fArr);
        ofFloat.setDuration(500L);
        Interpolator interpolator2 = this.interpolator;
        if (interpolator2 != null) {
            ofFloat.setInterpolator(interpolator2);
        }
        this.animatorSet.play(ofFloat);
        ofFloat.start();
        StateListener stateListener2 = this.stateListener;
        if (stateListener2 != null) {
            if (this.backdropShown) {
                stateListener2.onOpen(ofFloat);
            } else {
                stateListener2.onClose(ofFloat);
            }
        }
        return ofFloat;
    }

    private int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        if (this.context.getTheme().resolveAttribute(16843499, typedValue, true)) {
            return TypedValue.complexToDimensionPixelSize(typedValue.data, this.displayMetrics);
        }
        return -1;
    }

    public ObjectAnimator open() {
        return open(null);
    }

    public ObjectAnimator open(View view) {
        this.backdropShown = false;
        return toggle(view);
    }

    public ObjectAnimator close() {
        this.backdropShown = true;
        return toggle(this.buttonView);
    }

    private void updateIcon(View view) {
        Integer num;
        Integer num2 = this.openIcon;
        if (num2 != null && (num = this.closeIcon) != null) {
            if (num2 == null || num == null || (view instanceof ImageView)) {
                ImageView imageView = (ImageView) view;
                Context context2 = this.context;
                if (this.backdropShown) {
                    num2 = num;
                }
                imageView.setImageDrawable(ContextCompat.getDrawable(context2, num2.intValue()));
                if (this.closeIcon != null) {
                    imageView.setColorFilter(ContextCompat.getColor(this.context, this.colorIcon.intValue()), PorterDuff.Mode.SRC_IN);
                    return;
                }
                return;
            }
            Log.e(TAG, "updateIcon() must be called on an ImageView/ImageButton");
        }
    }

    public void setButtonView(View view) {
        this.buttonView = view;
    }

    public View getButtonView() {
        return this.buttonView;
    }

    public boolean isBackdropShown() {
        return this.backdropShown;
    }
}
