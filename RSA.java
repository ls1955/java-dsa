import java.lang.*;
import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
    private BigInteger p, q, n, lambda, e, d;
    private int bitLength = 1024;
    private SecureRandom r;

    public RSA() {
        r = new SecureRandom();
        p = new BigInteger(bitLength / 2, 100, new SecureRandom());
        q = new BigInteger(bitLength / 2, 100, new SecureRandom());
        n = p.multiply(q);
        lambda = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.valueOf(65537);
        while (lambda.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(lambda) < 0) {
            e.add(BigInteger.ONE);
        }
        d = e.modInverse(lambda);
    }

    public String getCiphertext(String plaintext) {
        BigInteger message = new BigInteger(plaintext.getBytes());
        BigInteger ciphertext = encrypt(message);
        return String.valueOf(ciphertext);
    }

    // Returns an encrypted number from *message*.
    public BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }

    public String getPlaintext(String ciphertext) {
        BigInteger message = new BigInteger(ciphertext);
        BigInteger decryptedMessage = decrypt(message);

        return new String(decryptedMessage.toByteArray());
    }

    // Returns a decrypted number from *message*.
    public BigInteger decrypt(BigInteger message) {
        return message.modPow(d, n);
    }

    public static void main(String[] args) {
        RSA rsa = new RSA();
        String plaintext = "tuna vanilla Ham Yam Bam";
        String ciphertext = rsa.getCiphertext(plaintext);
        String decryptedText = rsa.getPlaintext(ciphertext);

        System.out.println("plaintext: " + plaintext);
        System.out.println("ciphertext: " + ciphertext);
        System.out.println("decrypted text: " + decryptedText);
    }
}
