package zzuli.zw.blog.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.springframework.data.redis.core.StringRedisTemplate;

public class KeyUtils {

    public static String checkPassword(String password, String publicKey, StringRedisTemplate redisTemplate){
        // publicKey需要前端返回
        String privateKey = redisTemplate.opsForValue().get(publicKey);
        //String privateKey = (String) session.getAttribute(publicKey);
        if(privateKey ==null){
            return null;
        }
        // 获取到私钥之后，需要将redis中的该公钥私钥对信息移除
        redisTemplate.opsForValue().getOperations().delete(publicKey);
        // 构建，当只用私钥进行构造对象时，只允许使用该私钥进行加密和解密操作，本文只需要进行私钥解密，故只使用私钥构造对象
        RSA rsa = new RSA(privateKey, null);
        // 密码的密文先进行base64解码，之后再进行解密
        byte[] decrypt = rsa.decrypt(Base64.decode(password), KeyType.PrivateKey);
        return StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8);
    }

    public static String checkPassOnReg(String password, String publicKey, StringRedisTemplate redisTemplate){
        // publicKey需要前端返回
        String privateKey = redisTemplate.opsForValue().get(publicKey);
        //String privateKey = (String) session.getAttribute(publicKey);
        if(privateKey ==null){
            return null;
        }
        // 构建，当只用私钥进行构造对象时，只允许使用该私钥进行加密和解密操作，本文只需要进行私钥解密，故只使用私钥构造对象
        RSA rsa = new RSA(privateKey, null);
        // 密码的密文先进行base64解码，之后再进行解密
        byte[] decrypt = rsa.decrypt(Base64.decode(password), KeyType.PrivateKey);
        return StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8);

    }
}
