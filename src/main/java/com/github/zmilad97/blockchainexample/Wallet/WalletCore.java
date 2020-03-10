package com.github.zmilad97.blockchainexample.Wallet;

import com.github.zmilad97.blockchainexample.Service.Cryptography;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class WalletCore {


    private String privateSignature;
    private String publicSignature;
    private double balance;
    Cryptography cryptography = new Cryptography();


    public WalletCore() {
        try {
            this.privateSignature = cryptography.toHexString(cryptography.getSha(randomStringGenerator()));
            this.publicSignature = cryptography.toHexString(cryptography.getSha(randomStringGenerator()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }




    public String randomStringGenerator() {

        final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        final String NUMBER = "0123456789";

        final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
        SecureRandom random = new SecureRandom();

        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {

            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            sb.append(rndChar);

        }
        return sb.toString();
    }


    public String getPublicSignature() {
        return publicSignature;
    }

    public void setPublicSignature(String publicSignature) {
        this.publicSignature = publicSignature;
    }

    public String getPrivateSignature() {
        return privateSignature;
    }

    public void setPrivateSignature(String privateSignature) {
        this.privateSignature = privateSignature;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
