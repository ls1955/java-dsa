import java.lang.*;
import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
    public BigInteger p, q, n, phi, e, d;
    private int bitLength = 1024;
    private SecureRandom r;

    public RSA() {
        r = new SecureRandom();
        p = new BigInteger(bitLength / 2, 100, new SecureRandom());
        q = new BigInteger(bitLength / 2, 100, new SecureRandom());
        n = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.valueOf(65537);
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
            e.add(BigInteger.ONE);
        }
        d = e.modInverse(phi);
    }

    public String getCiphertext(String plaintext) {
        BigInteger message = new BigInteger(plaintext.getBytes());
        BigInteger ciphertext = message.modPow(e, n);
        return String.valueOf(ciphertext);
    }

    public String getPlaintext(String ciphertext) {
        BigInteger message = new BigInteger(ciphertext);
        BigInteger decryptedMessage = message.modPow(d, n);

        return new String(decryptedMessage.toByteArray());
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
