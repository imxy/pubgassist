package com.pubg.sb.pubgassist.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import androidx.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.Button;

import com.pubg.sb.pubgassist.R;
import com.pubg.sb.pubgassist.adb.KeyCode;
import com.pubg.sb.pubgassist.adb.SuProcess;

import util.LogUtil;

/**
 * @author XY on 2019/6/3
 * @apiNote 悬浮窗 service
 */
public class FloatingService2 extends Service {
    public static boolean isStarted = false;

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private Button mBtnStop;
    private Thread mLoopThread;

    @Override
    public void onCreate() {
        super.onCreate();
        isStarted = true;
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.START | Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.x = 300;
        layoutParams.y = 300;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void showFloatingWindow() {
        if (Settings.canDrawOverlays(this)) {
            new Button(getApplicationContext());
            @SuppressLint("InflateParams")
            View floatView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_float2, null);
            windowManager.addView(floatView, layoutParams);
            FloatingOnTouchListener onTouchListener = new FloatingOnTouchListener();
            floatView.setOnTouchListener(onTouchListener);

            mBtnStop = floatView.findViewById(R.id.btnStop);
            mBtnStop.setOnClickListener(v -> stopLoop());
            mBtnStop.setOnTouchListener(onTouchListener);

            Button btnStart1 = floatView.findViewById(R.id.btnStart1);
            btnStart1.setOnClickListener(v -> mockClick1((Button) v));
            btnStart1.setOnTouchListener(onTouchListener);
        }
    }

    private void stopLoop() {
        count = 0;
        tag = false;
        mBtnStop.post(() -> mBtnStop.setText("停止"));
        if (null != mLoopThread) {
            mLoopThread.interrupt();
        }
    }

    private boolean tag = false;
    private int count = 0;

//    private Point[] mPoint1 ={new Point(209,250),new Point(209,806),new Point(209,1510),new Point(209,2075),
//            new Point(528,580),new Point(528,1125),new Point(528,2291),new Point(528,643),
//            new Point(885,250),new Point(885,806),new Point(885,1510),new Point(885,5075)};

    private Point[] mPoint1 = {new Point(212, 591), new Point(209, 895), new Point(209, 1223), new Point(209, 1550), new Point(272, 1900),
            new Point(528, 244), new Point(528, 1072), new Point(528, 1447), new Point(528, 1739), new Point(528, 2093),
            new Point(885, 591), new Point(885, 895), new Point(885, 1223), new Point(885, 1550), new Point(885, 1900)};

    private void mockClick1(final Button button) {
        mBtnStop.setText("进行中");
        tag = true;
        count = 0;


        executeLoop1(button);
    }

    private void executeLoop1(Button button) {
        if (!tag) return;

        mLoopThread = new Thread(() -> {
            try {
                Thread.sleep(100);
                Point point = mPoint1[count];
                clickQGD(point, button);
                Thread.sleep(12000);
                clickDMB(button);
                Thread.sleep(1200);
                back(button);
                Thread.sleep(1000);
                count++;
                if (count >= mPoint1.length) {
                    stopLoop();
                }
                executeLoop1(button);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        mLoopThread.start();
    }


    private void back(View view) {
        view.post(() -> {
            if (!tag) return;
            SuProcess.KeyClick(KeyCode.Back);
        });
    }


    //点击进入店铺
    private void clickQGD(Point point, View view) {
        view.post(() -> {
            if (!tag) return;
            SuProcess.ScreenClick(point.x, point.y);
            LogUtil.e("点击进入店铺");
        });
    }

    //点击得到猫币
    private void clickDMB(View view) {
        view.post(() -> {
            if (!tag) return;
            SuProcess.ScreenClick(965, 1128);
            LogUtil.e("点击得到猫币");
        });
    }

    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x + movedX;
                    layoutParams.y = layoutParams.y + movedY;

                    ViewParent parent = view.getParent();
                    if (null != parent) {
                        windowManager.updateViewLayout((View) parent, layoutParams);
                    } else {
                        windowManager.updateViewLayout(view, layoutParams);
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    }
}
