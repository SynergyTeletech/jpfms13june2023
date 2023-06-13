package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class AspectRatioImageView extends AppCompatImageView
{
    public AspectRatioImageView(Context context)
    {
        super(context);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), (getMeasuredWidth() / 4));
    }
}

