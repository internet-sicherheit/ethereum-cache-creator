package de.internetsicherheit.brl.bloxberg.cache.ethereum;

public class TransactionAddress {
    public String address;
    public enum type {
        USER,
        SMARTCONTRACT,
        VALIDATOR
    }

    public TransactionAddress(String address) {
        this.address = address;
    }

}
