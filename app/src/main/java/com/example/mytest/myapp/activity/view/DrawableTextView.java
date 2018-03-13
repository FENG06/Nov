package com.example.mytest.myapp.activity.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Zhou on 2017/11/15.
 */

public class DrawableTextView extends android.support.v7.widget.AppCompatTextView {

    public DrawableLeftClickListener drawableLeftClickListener;


    public DrawableTextView(Context context) {
        super(context);
    }

    public DrawableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public DrawableLeftClickListener getDrawableLeftClickListener() {
        return drawableLeftClickListener;
    }

    public void setDrawableLeftClick(DrawableLeftClickListener drawableLeftClickListener) {
        this.drawableLeftClickListener = drawableLeftClickListener;
    }

    public interface DrawableLeftClickListener {
        void onDrawableLeftClickListener(DrawableTextView drawableTextView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (drawableLeftClickListener != null) {
                    Drawable leftDrawable = getCompoundDrawables()[0];

                    if ((leftDrawable != null) && (event.getX() <= leftDrawable.getBounds().width() + 20)) {

                        drawableLeftClickListener.onDrawableLeftClickListener(this);
                    }

                    return false;
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
