package com.urrecliner.myholybible;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class ZoomInOutListener implements View.OnTouchListener {

    private ScaleGestureDetector scaleGestureDetector;

    ZoomInOutListener(Context c) {

        scaleGestureDetector = new ScaleGestureDetector(c, new MyOnScaleGestureListener());
    }

    ScaleGestureDetector getScaleGestureDetector() {
        return scaleGestureDetector;
    }

    public boolean onTouch(final View view, final MotionEvent motionEvent) {


        return scaleGestureDetector.onTouchEvent(motionEvent);
    }

//    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
//
//        private static final int SWIPE_THRESHOLD = 500;
//
//        @Override
//        public boolean onDown(MotionEvent e) {
//            return true;
//        }
//        @Override
//        public boolean onSingleTapUp(MotionEvent e) {
//            onClick();
//            return super.onSingleTapUp(e);
//        }
//        @Override
//        public boolean onDoubleTap(MotionEvent e) {
////            onDoubleClick(e);
//            return super.onDoubleTap(e);
//        }
//        @Override
//        public void onLongPress(MotionEvent e) {
//            Log.w("long","clicked");
//            onLongClick();
//            super.onLongPress(e);
//        }
//        @Override
//        public boolean onDoubleTap(MotionEvent e) {
//            zoomText();
//            return super.onDoubleTap(e);
//        }
//        @Override
//        public void onLongPress(MotionEvent e) {
//            onLongClick();
//            super.onLongPress(e);
//        }
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            try {
//                float e1X = e1.getX();
//                float e2X = e2.getX();
//                float e1Y = e1.getY();
//                float e2Y = e2.getY();
//                float diffX = e1X - e2X;
////                utils.log("x ", e1X+" > "+e2X+" d= "+diffX+" y=" + e1Y+" cnt1 "+e1.getPointerCount()+" cnt2 "+e1.getPointerCount());
//                if (diffX > SWIPE_THRESHOLD && e2Y < windowYUpper)
//                    onSwipeGoFore();
//                else if (diffX < -SWIPE_THRESHOLD && e2Y < windowYUpper)
//                    onSwipeGoBack();
//                else if (diffX > SWIPE_THRESHOLD && e1Y > windowYUpper && e2Y > windowYUpper)
//                    onSwipeNext();
//                else if (diffX < -SWIPE_THRESHOLD && e1Y > windowYUpper && e2Y > windowYUpper)
//                    onSwipePrev();
//            } catch (Exception exception) {
//                exception.printStackTrace();
//            }
//            return false;
//        }
//    }
//
//    public void onSwipeGoBack() { }
//    public void onSwipeGoFore() { }
//    public void onSwipePrev() { }
//    public void onSwipeNext() { }
//    public void zoomText() { }
//    private void onClick() { }
//    }

    public class MyOnScaleGestureListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            float scaleFactor = detector.getScaleFactor();
//            utils.log("Scale"," factor "+scaleFactor);
            if (scaleFactor > 1.25f)
                onZoomOut();
            else if (scaleFactor < 0.85f)
                onZoomIn();
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
//            utils.log("Scale"," Begin factor "+detector.getScaleFactor());
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
//            utils.log("Scale"," End factor "+detector.getScaleFactor());
        }

    }
    public void onZoomOut() { }
    public void onZoomIn() { }

}

//    private GestureDetector gestureDetector;

//    GestureDetector getGestureDetector() {
//        return gestureDetector;
//    }
