package com.qubic.grabsimulation.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.ScrollView;


public class SwipeRefreshLayout extends android.support.v4.widget.SwipeRefreshLayout {

    private ListView listView;

    private ScrollView scrollView;

    public SwipeRefreshLayout(Context context) {
        super(context);
        init();
    }

    public SwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
//        setColorSchemeResources(
//                R.color.swipe_color_1, R.color.swipe_color_2,
//                R.color.swipe_color_3, R.color.swipe_color_4
//        );
        setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    @Override
    public boolean canChildScrollUp() {
        if (null != listView) {
            return listView.canScrollVertically(-1);
        }

        if (null != scrollView) {
            return scrollView.canScrollVertically(-1);
        }

        return super.canChildScrollUp();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            //Fix for support lib bug, happening when onDestroy is triggered.
            return true;
        }
    }
}