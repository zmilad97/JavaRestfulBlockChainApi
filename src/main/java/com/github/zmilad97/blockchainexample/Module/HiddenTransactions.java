package com.github.zmilad97.blockchainexample.Module;

import com.github.zmilad97.blockchainexample.Wallet.WalletCore;

public class HiddenTransactions {
    private String id;
    private WalletCore source;
    private WalletCore destination;
    private double amount;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public WalletCore getSource() {
        return source;
    }

    public void setSource(WalletCore source) {
        this.source = source;
    }

    public WalletCore getDestination() {
        return destination;
    }

    public void setDestination(WalletCore destination) {
        this.destination = destination;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
