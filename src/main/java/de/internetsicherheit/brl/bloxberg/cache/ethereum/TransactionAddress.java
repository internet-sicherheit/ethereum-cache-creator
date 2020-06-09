package de.internetsicherheit.brl.bloxberg.cache.ethereum;

public class TransactionAddress {
    public String address;
    public Type label;

    public TransactionAddress(String address, Type label) {
        this.address = address;
        this.label = label;
    }

    public enum Type {
        USERORVALIDATOR,
        SMARTCONTRACT,
        NONE
    }

}
