package com.example.photoeditor.Classs;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/* loaded from: classes3.dex */
public class AutoScrollViewPager extends ViewPager {
    public static final int DEFAULT_INTERVAL = 1500;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int SCROLL_WHAT = 0;
    public static final int SLIDE_BORDER_MODE_CYCLE = 1;
    public static final int SLIDE_BORDER_MODE_NONE = 0;
    public static final int SLIDE_BORDER_MODE_TO_PARENT = 2;
    private double autoScrollFactor;
    private int direction;
    private float downX;
    private Handler handler;
    private long interval;
    private boolean isAutoScroll;
    private boolean isBorderAnimation;
    private boolean isCycle;
    private boolean isStopByTouch;
    private CustomDurationScroller scroller;
    private int slideBorderMode;
    private boolean stopScrollWhenTouch;
    private double swipeScrollFactor;
    private float touchX;

    public AutoScrollViewPager(Context paramContext) {
        super(paramContext);
        this.interval = 1500L;
        this.direction = 1;
        this.isCycle = true;
        this.stopScrollWhenTouch = true;
        this.slideBorderMode = 0;
        this.isBorderAnimation = true;
        this.autoScrollFactor = 1.0d;
        this.swipeScrollFactor = 1.0d;
        this.isAutoScroll = false;
        this.isStopByTouch = false;
        this.touchX = 0.0f;
        this.downX = 0.0f;
        this.scroller = null;
        init();
    }

    public AutoScrollViewPager(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        this.interval = 1500L;
        this.direction = 1;
        this.isCycle = true;
        this.stopScrollWhenTouch = true;
        this.slideBorderMode = 0;
        this.isBorderAnimation = true;
        this.autoScrollFactor = 1.0d;
        this.swipeScrollFactor = 1.0d;
        this.isAutoScroll = false;
        this.isStopByTouch = false;
        this.touchX = 0.0f;
        this.downX = 0.0f;
        this.scroller = null;
        init();
    }

    private void init() {
        this.handler = new MyHandler(this);
        setViewPagerScroller();
    }

    public void startAutoScroll() {
        this.isAutoScroll = true;
        sendScrollMessage((long) (this.interval + ((this.scroller.getDuration() / this.autoScrollFactor) * this.swipeScrollFactor)));
    }

    public void startAutoScroll(int delayTimeInMills) {
        this.isAutoScroll = true;
        sendScrollMessage(delayTimeInMills);
    }

    public void stopAutoScroll() {
        this.isAutoScroll = false;
        this.handler.removeMessages(0);
    }

    public void setSwipeScrollDurationFactor(double scrollFactor) {
        this.swipeScrollFactor = scrollFactor;
    }

    public void setAutoScrollDurationFactor(double scrollFactor) {
        this.autoScrollFactor = scrollFactor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendScrollMessage(long delayTimeInMills) {
        this.handler.removeMessages(0);
        this.handler.sendEmptyMessageDelayed(0, delayTimeInMills);
    }

    private void setViewPagerScroller() {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolatorField = ViewPager.class.getDeclaredField("sInterpolator");
            interpolatorField.setAccessible(true);
            CustomDurationScroller customDurationScroller = new CustomDurationScroller(getContext(), (Interpolator) interpolatorField.get(null));
            this.scroller = customDurationScroller;
            scrollerField.set(this, customDurationScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scrollOnce() {
        int totalCount;
        PagerAdapter adapter = getAdapter();
        int currentItem = getCurrentItem();
        if (adapter == null || (totalCount = adapter.getCount()) <= 1) {
            return;
        }
        int nextItem = this.direction == 0 ? currentItem - 1 : currentItem + 1;
        if (nextItem < 0) {
            if (this.isCycle) {
                setCurrentItem(totalCount - 1, this.isBorderAnimation);
            }
        } else if (nextItem == totalCount) {
            if (this.isCycle) {
                setCurrentItem(0, this.isBorderAnimation);
            }
        } else {
            setCurrentItem(nextItem, true);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        if (this.stopScrollWhenTouch) {
            if (action == 0 && this.isAutoScroll) {
                this.isStopByTouch = true;
                stopAutoScroll();
            } else if (ev.getAction() == 1 && this.isStopByTouch) {
                startAutoScroll();
            }
        }
        int i = this.slideBorderMode;
        if (i == 2 || i == 1) {
            this.touchX = ev.getX();
            if (ev.getAction() == 0) {
                this.downX = this.touchX;
            }
            int currentItem = getCurrentItem();
            PagerAdapter adapter = getAdapter();
            int pageCount = adapter == null ? 0 : adapter.getCount();
            if ((currentItem == 0 && this.downX <= this.touchX) || (currentItem == pageCount - 1 && this.downX >= this.touchX)) {
                if (this.slideBorderMode == 2) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    if (pageCount > 1) {
                        setCurrentItem((pageCount - currentItem) - 1, this.isBorderAnimation);
                    }
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                return super.dispatchTouchEvent(ev);
            }
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class MyHandler extends Handler {
        private final WeakReference<AutoScrollViewPager> autoScrollViewPager;

        public MyHandler(AutoScrollViewPager autoScrollViewPager) {
            this.autoScrollViewPager = new WeakReference<>(autoScrollViewPager);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    AutoScrollViewPager pager = this.autoScrollViewPager.get();
                    if (pager != null) {
                        pager.scroller.setScrollDurationFactor(pager.autoScrollFactor);
                        pager.scrollOnce();
                        pager.scroller.setScrollDurationFactor(pager.swipeScrollFactor);
                        pager.sendScrollMessage(pager.interval + pager.scroller.getDuration());
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public long getInterval() {
        return this.interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public int getDirection() {
        return this.direction == 0 ? 0 : 1;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isCycle() {
        return this.isCycle;
    }

    public void setCycle(boolean isCycle) {
        this.isCycle = isCycle;
    }

    public boolean isStopScrollWhenTouch() {
        return this.stopScrollWhenTouch;
    }

    public void setStopScrollWhenTouch(boolean stopScrollWhenTouch) {
        this.stopScrollWhenTouch = stopScrollWhenTouch;
    }

    public int getSlideBorderMode() {
        return this.slideBorderMode;
    }

    public void setSlideBorderMode(int slideBorderMode) {
        this.slideBorderMode = slideBorderMode;
    }

    public boolean isBorderAnimation() {
        return this.isBorderAnimation;
    }

    public void setBorderAnimation(boolean isBorderAnimation) {
        this.isBorderAnimation = isBorderAnimation;
    }
}
