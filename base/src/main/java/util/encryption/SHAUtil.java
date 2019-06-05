package util.encryption;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 对外提供getSHA1(String str)方法
 *
 * @author randyjia
 */
public class SHAUtil {
    /**
     * 获取sha1加密字符串
     *
     * @param val 加密的盐？？
     */
    @SuppressWarnings("unused")
    public static String getSHA1(String val) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("SHA-1");
        md5.update(val.getBytes());
        byte[] m = md5.digest();//加密
        return getString(m);
    }

    private static String getString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte aB : b) {
            sb.append(aB);
        }
        return sb.toString();
    }

    /**
     * 带key的 sha1(HmacSha1)
     * @return 加密后的字节数组，可使用其它方式转化为需要的字符串
     */
    public static byte[] hmacSha1(String base, String key) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec secret = new SecretKeySpec(key.getBytes("UTF-8"), mac.getAlgorithm());
        mac.init(secret);
        return mac.doFinal(base.getBytes());
    }

}