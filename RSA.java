import java.lang.*;
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
        if (plaintext.length() < 20) {
            throw new IllegalArgumentException("plaintext length should be bigger or equal 20. got: " + plaintext.length());
        }

        // TODO: Rename variable
        BigInteger num = new BigInteger(plaintext.getBytes());
        BigInteger ciphertext = encrypt(num);
        return String.valueOf(ciphertext);
    }

    // Returns an encrypted number from *num*.
    public BigInteger encrypt(BigInteger num) {
        return num.modPow(e, n);
    }

    public String getPlaintext(String ciphertext) {
        if (ciphertext.length() == 0) {
            throw new IllegalArgumentException("could not work with empty ciphertext");
        }

        // TODO: Rename variable
        BigInteger num = new BigInteger(ciphertext.getBytes());
        BigInteger plaintext = decrypt(num);
        return String.valueOf(ciphertext);
    }

    // Returns a decrypted number from *num*.
    public BigInteger decrypt(BigInteger num) {
        return num.modPow(this.d, this.n);
    }
}
