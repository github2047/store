package org.example.store.pojo.biz;

import lombok.Data;
import lombok.experimental.Accessors;
import org.example.store.util.RSAKeys;
import org.example.store.util.RSAUtils;

import java.util.Base64;

@Data
@Accessors(chain = true)
public class RsaKey {
    private String pri;
    private String pub;

    public static RsaKey generate() {
        try {
            RSAKeys key = RSAUtils.createKey();
            RsaKey rsaKey = new RsaKey();
            rsaKey.setPri(Base64.getEncoder().encodeToString(key.getPrivateKey().getEncoded()));
            rsaKey.setPub(Base64.getEncoder().encodeToString(key.getPublicKey().getEncoded()));
            return rsaKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
