package com.duke.mfa.poc.utils;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.crypto.generators.PKCS12ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Base32;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.duke.mfa.poc.dto.Password;

/**
 * @author Kazi
 */
public class PasswordUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordUtils.class);

    /**
     * The number of iterations the "mixing" function is to be applied for. The
     * higher the number, the difficult it is for hackers as it increases the
     * computational cost.
     */
    private static final int DERIVATION_FUNCTION_ITERATION_COUNT = 1000;
    private static final int DEFAULT_SALT_LENGTH = 64;
    private static final int DEFAULT_HASH_KEY_LENGTH = 1024;

    /**
     * Generates a seed of length 64 using {@link SecureRandom}.
     * 
     * @return
     */
    public static byte[] generateSalt() {
        return generateSalt(DEFAULT_SALT_LENGTH);
    }

    /**
     * Generates a seed of specified length using {@link SecureRandom}.
     * 
     * @param length
     * @return
     */
    public static byte[] generateSalt(int length) {
        SecureRandom random = new SecureRandom();
        return random.generateSeed(length);
    }

    /**
     * Uses a default hash key length and iteration count
     * 
     * @param password - The password from which the hash has to be calculated.
     * @param salt     - The salt that should be added to the hash.
     * @return
     */
    public static byte[] generateHash(String password, byte[] salt) {
        return generateHash(password, salt, DEFAULT_HASH_KEY_LENGTH, DERIVATION_FUNCTION_ITERATION_COUNT);
    }

    /**
     * 
     * @param password
     * @param salt
     * @param hashKeyLength
     * @param iterationCount
     * @return
     */
    public static byte[] generateHash(String password, byte[] salt, int hashKeyLength, int iterationCount) {
        PKCS12ParametersGenerator keyDerivationFunction = new PKCS12ParametersGenerator(new SHA3Digest());
        keyDerivationFunction.init(password.getBytes(), salt, iterationCount);
        KeyParameter cipherParameters = (KeyParameter) keyDerivationFunction
                .generateDerivedMacParameters(hashKeyLength);
        byte[] hash = cipherParameters.getKey();
        return hash;
    }

    /**
     * Validates whether a password matches with the stored hash and salt.
     * 
     * @param password
     * @param hash
     * @param salt
     * @return
     */
    public static boolean validatePassword(String password, byte[] hash, byte[] salt) {
        byte[] newHash = generateHash(password, salt);
        LOGGER.info("DB Hash:  " + new String(Hex.encode(hash)));
        LOGGER.info("New Hash: " + new String(Hex.encode(newHash)));
        return Arrays.equals(hash, newHash);
    }

    /**
     * An API on top of generate hash and salts. This abstracts the details of
     * hashing and salting and provides an easier way.
     * 
     * @param password
     * @return
     */
    public static Password convertToHashedPassword(String password) {
        byte[] salt = generateSalt(DEFAULT_SALT_LENGTH);
        byte[] hash = generateHash(password, salt, DEFAULT_HASH_KEY_LENGTH, DERIVATION_FUNCTION_ITERATION_COUNT);
        return new Password(hash, salt);
    }

    /**
     * Validates whether the TOTP code is in valid format.
     * 
     * @param TotpCode
     * @return
     */
    public static boolean validateTotp(String TotpCode) {
        try {
            Long.parseLong(TotpCode);
        } catch (NumberFormatException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Convert a char[] totp secret to a base32 encoded string.
     * 
     * @param totpSecret
     * @return
     */
    public static String getBase32TotpSecret(char[] totpSecret) {
        return new String(Base32.encode(new String(totpSecret).getBytes()));
    }

    /**
     * Generates a secure random password. minimum length is 12
     * 
     * @param length - If a length is specified less than 12, still 12 is used.
     * @return
     */
    public static char[] generatePassword(int length) {
        return generatePassword(length, false);
    }

    /**
     * Generates a secure random password. minimum length is 12
     * 
     * @param length - If a length is specified less than 12, still 12 is used.
     * @param easy   - default is false. If easy, special characters are not used.
     * @return
     */
    public static char[] generatePassword(int length, boolean easy) {
        //
        if (length < 12) {
            LOGGER.warn("Generate password is generating with 12 characters instead of " + length + ".");
            length = 12;
        }
        char[] token = null;
        if (easy) {
            int additionalChars = length % 2;
            int intchars = length / 2, chars = length / 2 + additionalChars;
            Stream<Character> allCharStream = Stream.concat(generateRandomChars(intchars, CharType.INT),
                    generateRandomChars(chars, CharType.CHAR));
            List<Character> allChars = allCharStream.collect(Collectors.toList());
            Collections.shuffle(allChars);
            token = allChars.stream().map(String::valueOf).collect(Collectors.joining()).toCharArray();
        } else {
            int additionalChars = length % 3;
            int intchars = length / 3, chars = length / 3 + additionalChars, specials = length / 3;
            Stream<Character> allCharStream = Stream.concat(generateRandomChars(intchars, CharType.INT), Stream.concat(
                    generateRandomChars(chars, CharType.CHAR), generateRandomChars(specials, CharType.SPECIAL)));
            List<Character> allChars = allCharStream.collect(Collectors.toList());
            Collections.shuffle(allChars);
            token = allChars.stream().map(String::valueOf).collect(Collectors.joining()).toCharArray();
        }
        return token;
    }

    @SuppressWarnings("unchecked")
    private static <T> Stream<T> generateRandomChars(int count, CharType charType) {
        SecureRandom random = new SecureRandom();
        switch (charType) {
        case SPECIAL -> {
            Stream<Character> stream = random.ints(count / 4, 41, 47).mapToObj(e -> (char) e);
            Stream<Character> stream2 = random.ints(count / 4, 95, 96).mapToObj(e -> (char) e);
            Stream<Character> stream3 = random.ints(count / 4, 123, 127).mapToObj(e -> (char) e);
            Stream<Character> stream4 = random.ints((count / 4) + (count % 4), 58, 65).mapToObj(e -> (char) e);
            return (Stream<T>) Stream.concat(stream4, Stream.concat(stream, Stream.concat(stream2, stream3)));
        }
        case CHAR -> {
            Stream<Character> stream = random.ints(count / 2, 65, 91).mapToObj(e -> (char) e);
            Stream<Character> finalStream = Stream.concat(stream,
                    random.ints(count / 2, 97, 123).mapToObj(e -> (char) e));
            return (Stream<T>) finalStream;
        }
        case INT -> {
            Stream<Character> stream = random.ints(count, 48, 58).mapToObj(e -> (char) e);
            return (Stream<T>) stream;
        }
        default -> throw new IllegalArgumentException("Unexpected value: " + charType);
        }

    }

    public static enum CharType {
        INT, CHAR, SPECIAL
    }

}
