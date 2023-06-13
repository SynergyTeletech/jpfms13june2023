package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.custom.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;


public class LatoRegularTextView extends AppCompatTextView {

    public LatoRegularTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LatoRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LatoRegularTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/lato_regular.ttf");
        setTypeface(tf);

    }

}
