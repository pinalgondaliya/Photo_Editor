package com.example.photoeditor.Classs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.photoeditor.R;

import java.util.List;

/* loaded from: classes3.dex */
public class PolishCarouselPicker extends ViewPager {
    public static final int[] CarouselPicker = {R.attr.item_width};
    private Context context;
    private int itemWidth;

    /* loaded from: classes3.dex */
    public interface PickerItem {
        Bitmap getBitmap();

        String getColor();

        Drawable getDrawable();

        boolean hasDrawable();
    }

    public PolishCarouselPicker(Context context2) {
        this(context2, null);
        this.context = context2;
    }

    public PolishCarouselPicker(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        this.context = context2;
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        setClipChildren(false);
        setFadingEdgeLength(0);
        @SuppressLint("ResourceType") TypedArray obtainStyledAttributes = this.context.obtainStyledAttributes(attributeSet, CarouselPicker, 0, 0);
        this.itemWidth = obtainStyledAttributes.getInt(0, 15);
        obtainStyledAttributes.recycle();
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public void onMeasure(int i, int i2) {
        int i3 = 0;
        for (int i4 = 0; i4 < getChildCount(); i4++) {
            View childAt = getChildAt(i4);
            childAt.measure(i, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int measuredHeight = childAt.getMeasuredHeight();
            if (measuredHeight > i3) {
                i3 = measuredHeight;
            }
        }
        super.onMeasure(i, MeasureSpec.makeMeasureSpec(i3, MeasureSpec.EXACTLY));
        setPageMargin((-getMeasuredWidth()) + SystemUtil.dpToPx(this.context, this.itemWidth));
    }

    @Override // androidx.viewpager.widget.ViewPager
    public void setAdapter(PagerAdapter pagerAdapter) {
        super.setAdapter(pagerAdapter);
        setOffscreenPageLimit(pagerAdapter.getCount());
    }

    /* loaded from: classes3.dex */
    public static class CarouselViewAdapter extends PagerAdapter {
        Context context;
        int drawable;
        List<PickerItem> items;
        int textColor = 0;

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public CarouselViewAdapter(Context context2, List<PickerItem> list, int i) {
            this.context = context2;
            this.drawable = i;
            this.items = list;
            if (i == 0) {
                this.drawable = R.layout.item_carousel;
            }
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return this.items.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public Object instantiateItem(ViewGroup viewGroup, int i) {
            View inflate = LayoutInflater.from(this.context).inflate(this.drawable, (ViewGroup) null);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.icon);
            View findViewById = inflate.findViewById(R.id.color);
            PickerItem pickerItem = this.items.get(i);
            imageView.setVisibility(View.VISIBLE);
            if (pickerItem.hasDrawable()) {
                imageView.setVisibility(View.VISIBLE);
                findViewById.setVisibility(View.GONE);
                imageView.setImageDrawable(pickerItem.getDrawable());
            } else if (pickerItem.getColor() != null) {
                imageView.setVisibility(View.GONE);
                findViewById.setVisibility(View.VISIBLE);
                findViewById.setBackgroundColor(Color.parseColor(pickerItem.getColor()));
            }
            inflate.setTag(Integer.valueOf(i));
            viewGroup.addView(inflate);
            return inflate;
        }

        public int getTextColor() {
            return this.textColor;
        }

        public void setTextColor(int i) {
            this.textColor = i;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }
    }

    /* loaded from: classes3.dex */
    public static class ColorItem implements PickerItem {
        private String color;

        @Override // com.example.photoareditor.Classs.PolishCarouselPicker.PickerItem
        public Bitmap getBitmap() {
            return null;
        }

        @Override // com.example.photoareditor.Classs.PolishCarouselPicker.PickerItem
        public Drawable getDrawable() {
            return null;
        }

        @Override // com.example.photoareditor.Classs.PolishCarouselPicker.PickerItem
        public boolean hasDrawable() {
            return false;
        }

        public ColorItem(String str) {
            this.color = str;
        }

        @Override // com.example.photoareditor.Classs.PolishCarouselPicker.PickerItem
        public String getColor() {
            return this.color;
        }
    }

    /* loaded from: classes3.dex */
    public static class DrawableItem implements PickerItem {
        private Bitmap bitmap;
        private Drawable drawable;

        @Override // com.example.photoareditor.Classs.PolishCarouselPicker.PickerItem
        public String getColor() {
            return null;
        }

        @Override // com.example.photoareditor.Classs.PolishCarouselPicker.PickerItem
        public boolean hasDrawable() {
            return true;
        }

        public DrawableItem(Drawable drawable2) {
            this.drawable = drawable2;
            this.bitmap = ((BitmapDrawable) drawable2).getBitmap();
        }

        @Override // com.example.photoareditor.Classs.PolishCarouselPicker.PickerItem
        public Drawable getDrawable() {
            return this.drawable;
        }

        @Override // com.example.photoareditor.Classs.PolishCarouselPicker.PickerItem
        public Bitmap getBitmap() {
            return this.bitmap;
        }
    }
}
