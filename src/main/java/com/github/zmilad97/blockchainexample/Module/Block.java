package com.github.zmilad97.blockchainexample.Module;

import com.github.zmilad97.blockchainexample.Service.Cryptography;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

public class Block {
    private int index;
    private Date date;
    private String hash;
    private String previousHash;
    private long nonce;
    private List<Transactions> transactions;

    public Block(int index, Date date, List<Transactions> transactions) {
        this.index = index;
        this.date = date;
        this.transactions = transactions;

    }

    public Block() {
    }

    public String computeHash(String condition) {

        String hash = "null";
        nonce = -1;

        System.out.println(condition);
        try {
            do {
                nonce++;
                String stringtohash = nonce + this.index + this.date.toString() + this.previousHash + this.transactions;

                Cryptography cryptography = new Cryptography();
                hash = cryptography.toHexString(cryptography.getSha(stringtohash));

                if (hash.startsWith(condition))
                    break;

            } while (true);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        setNonce(nonce);
        return hash;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public List<Transactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transactions> transactions) {
        this.transactions = transactions;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }


    public void addTransaction(Transactions transaction) {
        transactions.add(transaction);
    }
}
