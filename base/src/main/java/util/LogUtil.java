package util;

import android.text.TextUtils;
import android.util.Log;


/**
 * @author XY
 * @apiNote 日志打印
 */


public class LogUtil {

    public static boolean IS_LOG_DEBUG = true;
    public static final String TAG = "PUBG";
    private static final int MAX_SIZE = 3000;

    public static void i(String message) {
        if (IS_LOG_DEBUG) {
            if (!TextUtils.isEmpty(message)) {
                if (message.length() <= MAX_SIZE) {
                    Log.i(TAG, message);
                } else {
                    splitPrint(message);
                }
            }
        }
    }

    public static void d(String message) {
        if (IS_LOG_DEBUG) {
            if (!TextUtils.isEmpty(message)) {
                if (message.length() <= MAX_SIZE) {
                    Log.d(TAG, message);
                } else {
                    splitPrintD(message);
                }
            }
        }
    }


    public static void e(String msg) {
        if (IS_LOG_DEBUG) {
            if (!TextUtils.isEmpty(msg)) {
                if (msg.length() <= MAX_SIZE) {
                    Log.e(TAG, msg);
                } else {
                    splitPrintE(msg);
                }
            }
        }
    }


    /**
     * 采用分段打印String
     */
    private static void splitPrint(String content) {
        if (!IS_LOG_DEBUG) {
            return;
        }

        final int sourseLength = content.length();
        // 游标
        int offset = 0;
        // 循环次数
        int i = 0;
        // 每次截取的字符
        String cache;

        while (offset < sourseLength) {

            if (sourseLength - offset > MAX_SIZE) {
                cache = content.substring(offset, offset + MAX_SIZE);
            } else {
                cache = content.substring(offset, sourseLength);
            }

            i++; // 自增
            offset = i * MAX_SIZE;

            // 处理分段内容
            Log.i(TAG, "分段打印 " + i + "：" + cache);
        }
    }

    /**
     * 采用分段打印String
     */
    private static void splitPrintD(String content) {
        if (!IS_LOG_DEBUG) {
            return;
        }

        final int sourseLength = content.length();
        // 游标
        int offset = 0;
        // 循环次数
        int i = 0;
        // 每次截取的字符
        String cache;

        while (offset < sourseLength) {

            if (sourseLength - offset > MAX_SIZE) {
                cache = content.substring(offset, offset + MAX_SIZE);
            } else {
                cache = content.substring(offset, sourseLength);
            }

            i++; // 自增
            offset = i * MAX_SIZE;

            // 处理分段内容
            Log.d(TAG, "分段打印 " + i + "：" + cache);
        }
    }


    /**
     * 采用分段打印String
     */
    private static void splitPrintE(String content) {
        if (!IS_LOG_DEBUG) {
            return;
        }

        final int sourseLength = content.length();
        // 游标
        int offset = 0;
        // 循环次数
        int i = 0;
        // 每次截取的字符
        String cache;

        while (offset < sourseLength) {

            if (sourseLength - offset > MAX_SIZE) {
                cache = content.substring(offset, offset + MAX_SIZE);
            } else {
                cache = content.substring(offset, sourseLength);
            }

            i++; // 自增
            offset = i * MAX_SIZE;

            // 处理分段内容
            Log.e(TAG, "分段打印 " + i + "：" + cache);
        }
    }
}
