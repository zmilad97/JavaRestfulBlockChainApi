package com.github.zmilad97.blockchainexample.Module;

import com.github.zmilad97.blockchainexample.Service.UserService;
import com.github.zmilad97.blockchainexample.Wallet.WalletCore;

public class Transactions {

    private String transactionId;
    private String source;
    private String destination;
    private double amount;

    public Transactions(Transactions transactions) {
        this.transactionId = transactions.transactionId;
        this.source = transactions.source;
        this.destination = transactions.destination;
        this.amount = transactions.amount;

    }

    public Transactions() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String trxid) {
        this.transactionId = trxid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
