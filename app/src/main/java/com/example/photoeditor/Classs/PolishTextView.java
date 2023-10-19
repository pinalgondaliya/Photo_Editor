package com.example.photoeditor.Classs;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

/* loaded from: classes3.dex */
public class PolishTextView extends Sticker {
    private int backgroundAlpha;
    private int backgroundBorder;
    private int backgroundColor;
    private BitmapDrawable backgroundDrawable;
    private final Context context;
    private Drawable drawable;
    private boolean isShowBackground;
    private float maxTextSizePixels;
    private float minTextSizePixels;
    private int paddingHeight;
    private int paddingWidth;
    private PolishText polishText;
    private StaticLayout staticLayout;
    private String text;
    private Layout.Alignment textAlign;
    private int textAlpha;
    private int textColor;
    private int textHeight;
    private PolishText.TextShadow textShadow;
    private int textWidth;
    private float lineSpacingExtra = 0.0f;
    private float lineSpacingMultiplier = 1.0f;
    private final TextPaint textPaint = new TextPaint(1);

    public PolishTextView(Context context2, PolishText polishText2) {
        this.context = context2;
        this.polishText = polishText2;
        PolishTextView textColor2 = setTextSize(polishText2.getTextSize()).setTextWidth(polishText2.getTextWidth()).setTextHeight(polishText2.getTextHeight()).setText(polishText2.getText()).setPaddingWidth(SystemUtil.dpToPx(context2, polishText2.getPaddingWidth())).setBackgroundBorder(SystemUtil.dpToPx(context2, polishText2.getBackgroundBorder())).setTextShadow(polishText2.getTextShadow()).setTextColor(polishText2.getTextColor()).setTextAlpha(polishText2.getTextAlpha()).setBackgroundColor(polishText2.getBackgroundColor()).setBackgroundAlpha(polishText2.getBackgroundAlpha()).setShowBackground(polishText2.isShowBackground()).setTextColor(polishText2.getTextColor());
        AssetManager assets = context2.getAssets();
        textColor2.setTypeface(Typeface.createFromAsset(assets, "fonts/" + polishText2.getFontName())).setTextAlign(polishText2.getTextAlign()).setTextShare(polishText2.getTextShader()).resizeText();
    }

    private float convertSpToPx(float f) {
        return this.context.getResources().getDisplayMetrics().scaledDensity * f;
    }

    @Override // com.example.photoareditor.Classs.Sticker
    public void draw(Canvas canvas) {
        Matrix matrix = getMatrix();
        canvas.save();
        canvas.concat(matrix);
        if (this.isShowBackground) {
            Paint paint = new Paint();
            if (this.backgroundDrawable != null) {
                paint.setShader(new BitmapShader(this.backgroundDrawable.getBitmap(), Shader.TileMode.MIRROR, Shader.TileMode.MIRROR));
                paint.setAlpha(this.backgroundAlpha);
            } else {
                paint.setARGB(this.backgroundAlpha, Color.red(this.backgroundColor), Color.green(this.backgroundColor), Color.blue(this.backgroundColor));
            }
            float f = this.textWidth;
            float f2 = this.textHeight;
            int i = this.backgroundBorder;
            canvas.drawRoundRect(0.0f, 0.0f, f, f2, i, i, paint);
            canvas.restore();
            canvas.save();
            canvas.concat(matrix);
        }
        canvas.restore();
        canvas.save();
        canvas.concat(matrix);
        canvas.translate(this.paddingWidth, (this.textHeight / 2) - (this.staticLayout.getHeight() / 2));
        this.staticLayout.draw(canvas);
        canvas.restore();
        canvas.save();
        canvas.concat(matrix);
        canvas.restore();
    }

    public PolishText getPolishText() {
        return this.polishText;
    }

    @Override // com.example.photoareditor.Classs.Sticker
    public int getAlpha() {
        return this.textPaint.getAlpha();
    }

    @Override // com.example.photoareditor.Classs.Sticker
    public Drawable getDrawable() {
        return this.drawable;
    }

    @Override // com.example.photoareditor.Classs.Sticker
    public int getHeight() {
        return this.textHeight;
    }

    public String getText() {
        return this.text;
    }

    @Override // com.example.photoareditor.Classs.Sticker
    public int getWidth() {
        return this.textWidth;
    }

    @Override // com.example.photoareditor.Classs.Sticker
    public void release() {
        super.release();
        if (this.drawable != null) {
            this.drawable = null;
        }
    }

    public PolishTextView resizeText() {
        String text2 = getText();
        if (text2 != null && text2.length() > 0) {
            PolishText.TextShadow textShadow2 = this.textShadow;
            if (textShadow2 != null) {
                this.textPaint.setShadowLayer(textShadow2.getRadius(), this.textShadow.getDx(), this.textShadow.getDy(), this.textShadow.getColorShadow());
            }
            this.textPaint.setTextAlign(Paint.Align.LEFT);
            this.textPaint.setARGB(this.textAlpha, Color.red(this.textColor), Color.green(this.textColor), Color.blue(this.textColor));
            int i = this.textWidth - (this.paddingWidth * 2);
            this.staticLayout = new StaticLayout(this.text, this.textPaint, i <= 0 ? 100 : i, this.textAlign, this.lineSpacingMultiplier, this.lineSpacingExtra, true);
        }
        return this;
    }

    @Override // com.example.photoareditor.Classs.Sticker
    /* renamed from: setAlpha */
    public PolishTextView setAlpha(int i) {
        this.textPaint.setAlpha(i);
        return this;
    }

    public PolishTextView setBackgroundAlpha(int i) {
        this.backgroundAlpha = i;
        return this;
    }

    public PolishTextView setBackgroundBorder(int i) {
        this.backgroundBorder = i;
        return this;
    }

    public PolishTextView setBackgroundColor(int i) {
        this.backgroundColor = i;
        return this;
    }

    @Override // com.example.photoareditor.Classs.Sticker
    /* renamed from: setDrawable */
    public PolishTextView setDrawable(Drawable drawable2) {
        this.drawable = drawable2;
        return this;
    }

    public PolishTextView setPaddingWidth(int i) {
        this.paddingWidth = i;
        return this;
    }

    public PolishTextView setShowBackground(boolean z) {
        this.isShowBackground = z;
        return this;
    }

    public PolishTextView setText(String str) {
        this.text = str;
        return this;
    }

    public PolishTextView setTextAlign(int i) {
        if (i == 2) {
            this.textAlign = Layout.Alignment.ALIGN_NORMAL;
        } else if (i == 3) {
            this.textAlign = Layout.Alignment.ALIGN_OPPOSITE;
        } else if (i == 4) {
            this.textAlign = Layout.Alignment.ALIGN_CENTER;
        }
        return this;
    }

    public PolishTextView setTextAlpha(int i) {
        this.textAlpha = i;
        return this;
    }

    public PolishTextView setTextColor(int i) {
        this.textColor = i;
        return this;
    }

    public PolishTextView setTextHeight(int i) {
        this.textHeight = i;
        return this;
    }

    public PolishTextView setTextShadow(PolishText.TextShadow textShadow2) {
        this.textShadow = textShadow2;
        return this;
    }

    public PolishTextView setTextShare(Shader shader) {
        this.textPaint.setShader(shader);
        return this;
    }

    public PolishTextView setTextSize(int i) {
        this.textPaint.setTextSize(convertSpToPx(i));
        return this;
    }

    public PolishTextView setTextWidth(int i) {
        this.textWidth = i;
        return this;
    }

    public PolishTextView setTypeface(Typeface typeface) {
        this.textPaint.setTypeface(typeface);
        return this;
    }
}
