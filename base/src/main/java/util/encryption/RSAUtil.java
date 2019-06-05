package util.encryption;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * @author XY
 * @apiNote RSA 解密util
 */

public class RSAUtil {
    /**
      * 历史贷款数据 公钥
      */
    public static String PUBLIC_KEY = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAyKq6viNU2sDeM9n73Kyo\n" +
            "Xtoet8z8EtClus74UmyZb2JusW5JrjK5GvW+NKR8xsTOwpmvqeiz2U68cWW/yG07\n" +
            "mcJmyccakAfCVZO01Xav/Dk/inKkv84CQqi70YCP7LKC/rPwBCAAgGiXtxc+lxr8\n" +
            "YdO9q8LURprsQ14Uoa4tt2m+XajjB1p0ad7x8iUi5HN1K1Yl4nGGO4KOa3WkrO0j\n" +
            "eA7H8IM6C9NI4UzOlHIrVfvze9tASbtbTVUkeEHt0b+0O752QZnvnJlP63km4P9L\n" +
            "jLumztbmGusAv0t/XYDEK3i9YkvBP/+I2C1DDQeHQzGdYKs1gP1iFWM7yYtapGD6\n" +
            "ab9SygTHMU6CCQWBy6XFq5u5qm3hYH3+y76mco60MPTxt/q+c5Do+/UD6K+hN2qB\n" +
            "hkNMn8TOipoa4MWlxgeixleY2jDjVDV/AN4c3tsAVpW2vW2cBc52/5OVPOq9l3ML\n" +
            "CmOOJNmii7tYRX1WLojd8/2Oxjk9rNTyfR0EzuTPpSinfDDnIG5/rv5GQIwb8mk3\n" +
            "BywYGCBfyQkNjcUpCmz8EfpcT6mQdgK1zvejUeTcoeOeDhfGvLGf7rJn3Zb++JtE\n" +
            "xqBavnYBad8ViU2OMqqAohrUF3Wcsn5WmwzG8V4Ni8Ylnb4lycCJy1bROIBdkrDJ\n" +
            "/6b3E9Ubkkv7yVClP+nWmmkCAwEAAQ==";

    private static final String RSA = "RSA";

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }


    /**
     * 用公钥解密
     */
    public static byte[] decryptData(byte[] encryptedData, PublicKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(encryptedData);
        } catch (Exception e) {
            return null;
        }
    }


    public static byte[] decode(String s) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            decode(s, bos);
        } catch (IOException var3) {
            throw new RuntimeException();
        }

        return bos.toByteArray();
    }

    private static void decode(String s, OutputStream os) throws IOException {
        int i = 0;
        int len = s.length();

        while (true) {
            while (i < len && s.charAt(i) <= 32) {
                ++i;
            }

            if (i == len) {
                break;
            }

            int tri = (decode(s.charAt(i)) << 18) + (decode(s.charAt(i + 1)) << 12) + (decode(s.charAt(i + 2)) << 6) + decode(s.charAt(i + 3));
            os.write(tri >> 16 & 255);
            if (s.charAt(i + 2) == 61) {
                break;
            }

            os.write(tri >> 8 & 255);
            if (s.charAt(i + 3) == 61) {
                break;
            }

            os.write(tri & 255);
            i += 4;
        }

    }

    static int decode(char c) {
        if (c >= 65 && c <= 90) {
            return c - 65;
        } else if (c >= 97 && c <= 122) {
            return c - 97 + 26;
        } else if (c >= 48 && c <= 57) {
            return c - 48 + 26 + 26;
        } else {
            switch (c) {
                case '+':
                    return 62;
                case '/':
                    return 63;
                case '=':
                    return 0;
                default:
                    throw new RuntimeException("unexpected code: " + c);
            }
        }
    }
}
