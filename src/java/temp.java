
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class temp {

    public static void main(String args[]) {
        try {
            //generate public key and private key
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024); // key length
            KeyPair keyPair = keyPairGenerator.genKeyPair();
            String privateKeyString = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
            String publicKeyString = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            //print both keys
            System.out.println("rsa key pair generated\n");
            System.out.println("privateKey\n" + privateKeyString + "\n");
            System.out.println("publicKey\n" + publicKeyString + "\n\n");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
