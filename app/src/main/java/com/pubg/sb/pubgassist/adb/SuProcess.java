package com.pubg.sb.pubgassist.adb;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;

import java.io.DataOutputStream;
import java.io.File;
import java.io.OutputStream;

import util.LogUtil;

/**
 * @author XY on 2019/6/5
 * @apiNote 各种cmd root命令
 */
@SuppressWarnings("unused")
public class SuProcess {
    private static long dellayMills = 0;
    private static long AddMills = 0;

    /**
     * 添加延时
     */
    public static void Delay(long mills) {
        AddMills = mills;
        dellayMills += mills;
    }

    /**
     * 执行命令
     */
    public static void Exec(final String cmd) {
        Runnable r = new Runnable() {
            long AddMillsT = SuProcess.AddMills;

            @Override
            public void run() {
                try {
                    dellayMills -= AddMillsT;

                    Process process = Runtime.getRuntime().exec("su");    // 获取su进程

                    // 获取输出流
                    OutputStream outputStream = process.getOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                    dataOutputStream.writeBytes(cmd);
                    dataOutputStream.flush();
                    dataOutputStream.close();
                    outputStream.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        AddMills = 0;
        if (dellayMills <= 0) dellayMills = 0;
        new Handler().postDelayed(r, dellayMills);
    }

    /**
     * 模拟按键点击
     */
    public static void KeyClick(int key) {
        String cmd = "input keyevent " + key;
        Exec(cmd);
    }

    /**
     * 模拟屏幕点击坐标 x,y
     */
    public static void ScreenClick(int x, int y) {
        String cmd = "input tap " + x + " " + y;
        Exec(cmd);
        LogUtil.e("模拟屏幕点击坐标 ("+ x + "," + y+")");
    }

    /**
     * 模拟输入信息text
     */
    public static void InputText(String text) {
        String cmd = "input text '" + text + "'";
        Exec(cmd);
    }

    /**
     * 模拟滑动屏幕
     */
    public static void ScreenSwipe(final int x1, final int y1, final int x2, final int y2) {
        Delay(100);    // 滑动屏幕时执行延时

        String cmd = "input swipe" + " " + x1 + " " + y1 + " " + x2 + " " + y2;
        Exec(cmd);
    }

    /**
     * 向右滑动屏幕
     */
    public static void ScreenSwipe_Right(Context context) {
        DisplayMetrics screen = getScreeenSize(context);

        int space = screen.widthPixels / 4;

        int x1 = space;
        int y1 = screen.heightPixels / 2;
        int x2 = screen.widthPixels - space;
        int y2 = y1;

        ScreenSwipe(x1, y1, x2, y2);
    }

    /**
     * 向左滑动屏幕
     */
    public static void ScreenSwipe_Left(Context context) {
        DisplayMetrics screen = getScreeenSize(context);

        int space = screen.widthPixels / 4;

        int x1 = screen.widthPixels - space;
        int y1 = screen.heightPixels / 2;
        int x2 = space;
        int y2 = y1;

        ScreenSwipe(x1, y1, x2, y2);
    }

    /**
     * 向上滑动屏幕
     */
    public static void ScreenSwipe_Up(Context context) {
        DisplayMetrics screen = getScreeenSize(context);

        int x1 = screen.widthPixels / 2;
        int y1 = screen.heightPixels - 10;
        int x2 = x1;
        int y2 = 10;

        ScreenSwipe(x1, y1, x2, y2);
    }

    /**
     * 向下滑动屏幕
     */
    public static void ScreenSwipe_Down(Context context) {
        DisplayMetrics screen = getScreeenSize(context);

        int x1 = screen.widthPixels / 2;
        int y1 = 10;
        int x2 = x1;
        int y2 = screen.heightPixels - 10;

        ScreenSwipe(x1, y1, x2, y2);
    }

    /**
     * 获取屏幕分辨率
     */
    private static DisplayMetrics getScreeenSize(Context context) {

        // int screenWidth = dm.widthPixels; // 屏幕宽度
        // int screenHeight = dm.heightPixels; // 屏幕高度

        return context.getResources().getDisplayMetrics();
    }

    /**
     * 截取整个屏幕 截图
     *
     * @param filePath 图片保存的路径
     */
    public static void screenShot(String filePath) {
        String cmd = "screencap -p " + filePath;
        Exec(cmd);
    }

    /**
     * 截取整个屏幕 截图
     * 在 内部存储文件夹下
     */
    public static void screenShot() {
        String cmd = "screencap -p mnt/sdcard/" + System.currentTimeMillis() + ".jpg";
        Exec(cmd);
    }
}