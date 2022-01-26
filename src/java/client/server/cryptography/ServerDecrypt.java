package client.server.cryptography;

import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ServerDecrypt {

    private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK2fy+iTF19X1WEh561HT3xIZFzdBIXGDrYqflS5od+CugHYh5ws9GcrtPDvD0wVzcz1sisjUVp9qV6EP0HvI2gR0Bduh9RGQOjfO3X6AaegYPH7LdgOPfSRP36BqFoZ2g/9xeVNDqouIgsxpQXEG2fcvPXy7/znbVFfIBV1fKTRAgMBAAECgYBOToHy9VRXRhAQ7O/GsSDiVuwdDfnaM8ey1VuA7vBZFGa8KmKPR587YPUMrWVi1XqqdC3VUuqAO6jF3jOBk0+OARlEsXSeW7FehgEZmJnWpysYzGwUsEcxFzGuOXUp6KK+PtarJCBH5F5Y4Fap4rpONv7bYjrOommil8Z5tUpYAQJBAPzPIw91L7QxeyQY26Ko9YpuJXzUDw524fD1VIxjdVMYvUtR/1VrJAfR0BqesyQv3R/tcJgUR8uKbKGRVckaBpECQQCv0M0zSgFTlSbSFwSdDfm81F1Tb9OHGN9DcBG3BQQdB5/5PUPqEDxnodLJ+yGBT4hwlciohyJzjMuK0AWjclpBAkEAmPUqnVultTyK6Ngxa0UW6t6hx9j2EyZSpduJgDdc8R3yyfOerSvZ7PFBHBoRAUAEZiDfEuSSW9Aw0cCBSt94QQJAW8WxJwGNObTUQ7vSI6zk3EU5xdDMaGt6Nvhrts3+fKy7O11Ycvgwn6OfpODad2YtgR8eU6HeQNSQdo/pMY0CQQJAVVzwP8GKY3t+gbBjKryoY8C0Fst4GwEE0EsIFX3cUfuHrwwk1HUHHYfhUU3p+e5pJlhe8Xm0HR12rvqWdcewXA==";

    public static String decryptText(String secretKeyEncryption, String cipherText) {
        String plainText = "";
        try {
            //Get private key
            PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(PRIVATE_KEY));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(privateSpec);
            //Decrypt encrypted secret key using private key
            Cipher cipher1 = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            cipher1.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] secretKeyBytes = cipher1.doFinal(Base64.getDecoder().decode(secretKeyEncryption));
            SecretKey secretKey = new SecretKeySpec(secretKeyBytes, 0, secretKeyBytes.length, "AES");
            //Decrypt encrypted text using secret key
            byte[] raw = secretKey.getEncoded();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(cipherText));
            plainText = new String(original, Charset.forName("UTF-8"));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return plainText;
    }
}
