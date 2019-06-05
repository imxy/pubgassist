package util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.xy.base.BuildConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import util.encryption.Base64Util;


/**
 * SharedPreferences工具类
 */
public class SharedPreferencesUtils {

    // 用户信心
    public static final String USER_INFO = "user_info";

    public static final String KEY_TOKEN = "token";//


    public static final String KEY_USER_PHONE_INFO = "userphonenumber";//

    public static final String KEY_PHONE_INFO = "phonenumber";//
    public static final String KEY_INFO = "info";//

    public static final String KEY_CAR_NUMBER = "carnumber";//
    public static final String KEY_CAR_OWNER_NAME = "car_owner_name";//
    public static final String KEY_CAR_OWNER_ID = "car_owner_id";//
    public static final String KEY_PROVINCE = "province";//

    public static final String KEY_ADDR_ID = "add_id";//
    public static final String KEY_ADDR_ALL = "add_all";//
    public static final String KEY_ADDR_PERSON_NAME = "add_person_name";//
    public static final String KEY_ADDR_PERSON_TEL = "add_person_tel";//


    // "Syr": "李桥",
    public static final String KEY_CAR_SYR = "carsyr";//
    //local
    //latitude纬度
    //longitude经度
    public static final String KEY_LOCAL_LATITUDE = "locallatitude";//
    public static final String KEY_LOCAL_LONGITUDE = "locallongitude";//

    public static final String COMPLETE_CARINFO = "complete_carinfo";//

    private static final String TAG = SharedPreferencesUtils.class.getSimpleName();

    private static final String PREFERENCES_FILE = BuildConfig.APPLICATION_ID;

    private static SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private static SharedPreferencesUtils mInstance;

    public static SharedPreferencesUtils getInstance(Context context) {
        if (null == mInstance) {
            mInstance = new SharedPreferencesUtils(context);
        }
        return mInstance;
    }

    private SharedPreferencesUtils(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    /**
     * @apiNote 将对象 以 base64字符串形式 存储到 sp中
     */
    public void putObject(String key, Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            String strObjectBase64 = Base64Util.encodeBase64(byteArrayOutputStream.toByteArray());
            //JqlLogUtils.d(TAG, "putObject strObjectBase64 = " + strObjectBase64);
            mEditor.putString(key, strObjectBase64);
            mEditor.commit();
        } catch (IOException e) {
            Log.e("putObject",e.toString());
        } finally {
            try {
                if (null != byteArrayOutputStream) {
                    byteArrayOutputStream.close();
                }
                if (null != objectOutputStream) {
                    objectOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @apiNote 将对象 以 base64字符串形式 存储到 sp中
     */
    public void putObjectAsync(String key, Object object) {
        new Thread(() ->
                putObject(key, object)).start();
    }

    /**
     * @author XY
     * @apiNote 获取base64编码的字符串
     */
    public String getBase64(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        String strObjectBase64 = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            strObjectBase64 = Base64Util.encodeBase64(byteArrayOutputStream.toByteArray());
            return strObjectBase64;
        } catch (IOException e) {
            System.err.println(e.toString());
        } finally {
            try {
                if (null != byteArrayOutputStream) {
                    byteArrayOutputStream.close();
                }
                if (null != objectOutputStream) {
                    objectOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return strObjectBase64;
    }


    public Object getObject(String key) {
        try {
            String strObjectBase64 = mSharedPreferences.getString(key, "");
            if (strObjectBase64.length() == 0) {
//                Logger.d("getObject strObjectBase64.length() == 0");
                return null;
            }
//             Logger.d( "getObject strObjectBase64 = " + strObjectBase64);
            byte[] base64Bytes;
            base64Bytes = Base64Util.decodeBase64(strObjectBase64);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream objectInputStream;
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        } catch (StreamCorruptedException e) {
//             Logger.d( "getObject StreamCorruptedException");
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
//             Logger.d( "getObject ClassNotFoundException");
            //e.printStackTrace();
        } catch (IOException e) {
//             Logger.d( "getObject IOException");
            //e.printStackTrace();
        }
        return null;
    }

    public String getString(String key) {
        return mSharedPreferences.getString(key, "");
    }

    public String getString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public void putString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public float getFloat(String key) {
        return mSharedPreferences.getFloat(key, 0);
    }

    public void putFloat(String key, float value) {
        mEditor.putFloat(key, value);
        mEditor.commit();
    }

    public long getLong(String key) {
        return mSharedPreferences.getLong(key, 0);
    }

    public void putLong(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    public int getInt(String key) {
        return mSharedPreferences.getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    public void putInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public boolean getBoolean(String key, boolean defaultBoolean) {
        return mSharedPreferences.getBoolean(key, defaultBoolean);
    }

    public void putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public void removeValue(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }

    public void removeValueAsync(String key) {
        new Thread(() -> removeValue(key)).start();
    }

    public void clearAllValues() {
        mEditor.clear();
        mEditor.commit();
    }

}
