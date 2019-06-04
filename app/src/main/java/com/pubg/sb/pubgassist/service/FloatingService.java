package com.pubg.sb.pubgassist.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.pubg.sb.pubgassist.adb.KeyCode;
import com.pubg.sb.pubgassist.adb.SuProcess;

/**
 * @author XY on 2019/6/3
 * @apiNote 悬浮窗 service
 */
public class FloatingService extends Service {
    public static boolean isStarted = false;

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

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
        layoutParams.width = 500;
        layoutParams.height = 100;
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
            Button button = new Button(getApplicationContext());
            button.setText("开始");
            windowManager.addView(button, layoutParams);

            button.setOnTouchListener(new FloatingOnTouchListener());

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("PUBG", "点击了吗");
                    mockClick((Button) v);
                }
            });
        }
    }

    private boolean tag = false;
    private int count = 0;

    private void mockClick(final Button button) {
//        //生成点击坐标
//        int x = 1000;
//        int y = 1170;
//        //利用ProcessBuilder执行shell命令
//        String[] order = {
//                "input",
//                "tap",
//                "" + x,
//                "" + y
//        };
//
//        try {
//            new ProcessBuilder(order).start();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("PUBG", e.toString());
//        }

        if ("开始".contentEquals(button.getText())) {
            button.setText("停止");
            tag = true;
        } else {
            button.setText("开始");
            count = 0;
            tag = false;
        }

        executeLoop2(button);
    }

    @SuppressLint("CheckResult")
    private void executeLoop2(final View view) {
        if (!tag) return;

        clickLMB(view);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    clickQGD(view);
                    Thread.sleep(12000);
                    clickDMB(view);
                    Thread.sleep(1200);
                    back(view);
                    Thread.sleep(1000);
                    count++;
                    if (count > 50) {
                        tag = false;
                    }
                    executeLoop2(view);//开始循环

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void back(View view) {
        view.post(new Runnable() {
            @Override
            public void run() {
                if (!tag) return;
                SuProcess.KeyClick(KeyCode.Back);
            }
        });
    }

    //点击领猫币
    private void clickLMB(View view) {
        view.post(new Runnable() {
            @Override
            public void run() {
                if (!tag) return;
                SuProcess.ScreenClick(904, 1685);
                Log.e("MMMMMMM", "点击领猫币");
            }
        });
    }

    //点击去逛店
    private void clickQGD(View view) {
        view.post(new Runnable() {
            @Override
            public void run() {
                if (!tag) return;
                SuProcess.ScreenClick(916, 1363);
                Log.e("MMMMMMM", "点击去逛店");
            }
        });
    }

    //点击得到猫币
    private void clickDMB(View view) {
        view.post(new Runnable() {
            @Override
            public void run() {
                if (!tag) return;
                SuProcess.ScreenClick(965, 1128);
                Log.e("MMMMMMM", "点击得到猫币");
            }
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
                    windowManager.updateViewLayout(view, layoutParams);
                    break;
                default:
                    break;
            }
            return false;
        }
    }
}
