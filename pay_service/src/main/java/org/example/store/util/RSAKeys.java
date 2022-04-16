package org.example.store.util;

import lombok.Data;
import lombok.experimental.Accessors;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Data
@Accessors(chain = true)
public class RSAKeys {
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;
}
