package com.duke.mfa.poc.dto;

/**
 * @author Kazi
 */
public final class Password {

    private final byte[] hash;
    private final byte[] salt;

    /**
     * @param hash
     * @param salt
     */
    public Password(byte[] hash, byte[] salt) {
        super();
        this.hash = hash;
        this.salt = salt;
    }

    public byte[] getHash() {
        return hash;
    }

    public byte[] getSalt() {
        return salt;
    }

}
