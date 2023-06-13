package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.custom.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;


public class LatoBoldTextView extends AppCompatTextView {

    public LatoBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LatoBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LatoBoldTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/lato_bold.ttf");
//        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/latox_bold.ttf");
        setTypeface(tf);

    }

}
