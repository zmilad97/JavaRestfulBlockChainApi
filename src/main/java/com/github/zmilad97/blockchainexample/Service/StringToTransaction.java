package com.github.zmilad97.blockchainexample.Service;

import com.github.zmilad97.blockchainexample.Module.HiddenTransactions;
import com.github.zmilad97.blockchainexample.Module.Transactions;
import com.github.zmilad97.blockchainexample.Module.User;

import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class StringToTransaction {


    private String transactionId;
    private String privateSource;
    private String publicSource;
    private String destination;
    private double amount;
    private int trxid = 0;
    Cryptography cryptography = new Cryptography();
    public StringToTransaction(String privateSource, String publicSource, String destination, double amount) {
        this.transactionId = setTrxId();
        this.privateSource = privateSource;
        this.publicSource = publicSource;
        this.destination = destination;
        this.amount = amount;
    }

    public StringToTransaction(){
    }

    public HiddenTransactions convertToHiddenTransactions (String transactionId,String privateSource,String publicSource,String destination,double amount,UserService userService,User user){
        HiddenTransactions hiddenTransactions = new HiddenTransactions();

        hiddenTransactions.setId(transactionId);
        hiddenTransactions.setSource(userService.findSource(privateSource,publicSource ,user));
        hiddenTransactions.setDestination(userService.findWallet(destination,user));
        hiddenTransactions.setAmount(amount);

        return hiddenTransactions;
    }


    public Transactions convertToTransaction(String publicSource, String destination, double amount) {
        Transactions trx = new Transactions();

        trx.setTransactionId(transactionId);
        trx.setSource(publicSource);
        trx.setDestination(destination);
        trx.setAmount(amount);
        return trx;
    }

    public String setTrxId() {
        Random rand = new Random();
        String id = ++trxid + new java.util.Date().toString() + rand.nextInt();
        try {
            id = cryptography.toHexString(cryptography.getSha(id));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


    public String getPrivateSource() {
        return privateSource;
    }

    public void setPrivateSource(String privateSource) {
        this.privateSource = privateSource;
    }

    public String getPublicSource() {
        return publicSource;
    }

    public void setPublicSource(String publicSource) {
        this.publicSource = publicSource;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


}
