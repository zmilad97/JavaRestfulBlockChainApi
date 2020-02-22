package com.github.zmilad97.blockchainexample.Module;

public class Transactions {
    private String source;
    private String destination;
    private int amount;


    public Transactions(String source, String destination, int amount) {
        this.source = source;
        this.destination = destination;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
