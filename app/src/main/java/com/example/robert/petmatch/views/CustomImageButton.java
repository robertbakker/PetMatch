package com.example.robert.petmatch.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * Created by Robert on 13-09-15.
 */
public class CustomImageButton extends ImageButton {

    @Override
    protected void drawableStateChanged() {
        if(isPressed()){
            setScaleX(1.1f);
            setScaleY(1.1f);
            setAlpha(0.5f);
        }  else {
            setScaleX(1.0f);
            setScaleY(1.0f);
            setAlpha(1.0f);
        }
        super.drawableStateChanged();

    }

    public CustomImageButton(Context context) {
        super(context);
    }

    public CustomImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomImageButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
