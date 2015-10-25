package track.project.authorization;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/**
 * Created by Булат on 25.10.2015.
 */
public class Password {
    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS_NUMBER = 1000;
    private static final String ALGORITHM = "SHA-1";
    private static final String ENCODING = "UTF-8";
    private static final String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";
    private byte[] pass;
    private byte[] salt;

    public Password(String password) {
        try {
            salt = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM).generateSeed(SALT_LENGTH);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Exception hashing password in SecureRandom: " + e.getMessage());
        }
        pass = getHash(password);
    }

    public Password(byte[] pass, byte[] salt) {
        this.pass = pass;
        this.salt = salt;
    }

    private byte[] getHash(String password) {
        byte[] input = null; //???
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            digest.reset();
            digest.update(salt);
            input = digest.digest(password.getBytes(ENCODING));
            for (int i = 0; i < ITERATIONS_NUMBER; i++) {
                digest.reset();
                input = digest.digest(input);
            }
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Exception hashing password: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.err.println("Exception hashing password: " + e.getMessage());
        }
        return input;
    }

    public byte[] getPass() {
        return pass;
    }

    public void setPass(String password) {
        try {
            salt = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM).generateSeed(SALT_LENGTH);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Exception hashing password in SecureRandom: " + e.getMessage());
        }
        pass = getHash(password);
    }

    public boolean checkPassword(String input) {
        byte[] inputHash = getHash(input);
        return Arrays.equals(pass, inputHash);
    }

    public String toString() {
        return Base64.getEncoder().encodeToString(pass) + " " + Base64.getEncoder().encodeToString(salt);
    }
}
