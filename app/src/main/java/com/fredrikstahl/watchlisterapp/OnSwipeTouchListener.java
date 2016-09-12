package com.fredrikstahl.watchlisterapp;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by fredrikstahl on 1/9/2016.
 */
public class OnSwipeTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;

    public OnSwipeTouchListener (Context ctx) {
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

    }
}
