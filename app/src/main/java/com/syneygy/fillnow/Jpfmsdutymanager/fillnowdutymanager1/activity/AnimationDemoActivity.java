package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;


public class AnimationDemoActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_demo);
        Button btn = findViewById(R.id.btn_click);
        final TextView tv = findViewById(R.id.tv);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move the object at the X position 300
//                ObjectAnimator anim = ObjectAnimator.ofFloat(tv,"x",500);
//                anim.setDuration(3000);
//                anim.start();

                // Move the object at the Y position 400
                ObjectAnimator anim2 = ObjectAnimator.ofFloat(tv,"y",900);
                anim2.setDuration(3000);
              //  anim2.setStartDelay(3000);
                anim2.start();
            }
        });
    }
}
