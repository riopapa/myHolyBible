package com.urrecliner.andriod.myholybible;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import static com.urrecliner.andriod.myholybible.Vars.windowYUpper;

public class OnSwipeTouchListener implements View.OnTouchListener {

    private GestureDetector gestureDetector;

    public OnSwipeTouchListener(Context c) {
        gestureDetector = new GestureDetector(c, new GestureListener());
    }
    public GestureDetector getGestureDetector(){
        return  gestureDetector;
    }
    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 500;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            onClick();
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
//            onDoubleClick(e);
            return super.onDoubleTap(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            onLongClick();
            super.onLongPress(e);
        }

        // Determines the fling velocity and then fires the appropriate swipe event accordingly
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                float e1X = e1.getX(); float e2X = e2.getX(); float e1Y = e1.getY();float e2Y = e2.getY();
                float diffX = e1X - e2X;
//                Log.w("x ", e1X+" > "+e2X+" d= "+diffX+" y=" + e1Y);
                if (diffX > SWIPE_THRESHOLD && e2Y < windowYUpper)
                    onSwipeGoFore();
                else if (diffX < -SWIPE_THRESHOLD && e2Y < windowYUpper)
                    onSwipeGoBack();
                else if (diffX > SWIPE_THRESHOLD && e1Y > windowYUpper && e2Y > windowYUpper)
                    onSwipeNext();
                else if (diffX < -SWIPE_THRESHOLD && e1Y > windowYUpper && e2Y > windowYUpper)
                    onSwipePrev();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return false;
        }
    }

    public void onSwipeGoBack() { }

    public void onSwipeGoFore() { }

    public void onSwipePrev() { }

    public void onSwipeNext() { }

    public void onClick() { }

//    public void onDoubleClick(MotionEvent e) {
//        float eX = e.getX(); float eY = e.getY();
////        Log.w("doubleclick",eX+" , "+eY+" XCenter="+windowXCenter+" Yup="+windowYUpper);
//        if (eY > windowYUpper && eX < windowXCenter)
//            onSwipePrev();
//        else if (eY > windowYUpper && eX > windowXCenter)
//            onSwipeNext();
//    }

    public void onLongClick() { }
}
