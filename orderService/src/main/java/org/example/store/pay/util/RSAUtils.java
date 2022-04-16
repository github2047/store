package org.example.store.pay.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public final class RSAUtils {
    private static final String DEFAULT_ALGORITHM = "RSA";
    private static final int DEFAULT_KEY_SIZE = 2048;//设置长度
    public static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";
    public static final String SIGN_ALGORITHMS = "SHA256withRSA";
    public static final String RSA_TYPE = "RSA/ECB/PKCS1Padding";

    public static String sign(String content, String privateKey, String contentCharset) {
        return sign(content, Base64.decode(privateKey), contentCharset);
    }

    /**
     * 签名
     *
     * @param content
     * @param privateKey
     * @param contentCharset
     * @return
     */
    public static String sign(String content, byte[] privateKey, String contentCharset) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory keyFactory = KeyFactory.getInstance(DEFAULT_ALGORITHM);
            PrivateKey priKey = keyFactory.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes(contentCharset));
            byte[] signed = signature.sign();
            return Base64.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean verify(String content, String sign, String publicKey, String contentCharset) {
        return verify(content, sign, Base64.decode(publicKey), contentCharset);
    }

    /**
     * 验证签名
     *
     * @param content        验证内容
     * @param sign           签名字符串
     * @param publicKey      公钥
     * @param contentCharset 编码
     * @return
     */
    public static boolean verify(String content, String sign, byte[] publicKey, String contentCharset) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(DEFAULT_ALGORITHM);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(contentCharset));
            return signature.verify(Base64.decode(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}


