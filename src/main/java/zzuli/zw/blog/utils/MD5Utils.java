package zzuli.zw.blog.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName: MD5Utils
 * @date: 2020/7/10 9:53
 * @author https://github.com/lxclxc/MD5Utils
 * @Description: 一个开源的Java md5加密小工具
 */
public class MD5Utils {
    /**
     * 可以把一段文字转换为MD
     * @param text
     * @return md5
     */
    public static String encode(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] buffer = digest.digest(text.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : buffer) {
                int a = b & 0xff;
                String hex = Integer.toHexString(a);

                if (hex.length() == 1) {
                    hex = 0 + hex;
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
