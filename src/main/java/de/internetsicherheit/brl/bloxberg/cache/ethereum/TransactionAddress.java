package de.internetsicherheit.brl.bloxberg.cache.ethereum;

public class TransactionAddress {
    public String address;
    public type label;

    public TransactionAddress(String address) {
        this.address = address;
    }

    public enum type {
        USERORVALIDATOR,
        SMARTCONTRACT,
        NONE
    }

}
