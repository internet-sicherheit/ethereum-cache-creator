package de.internetsicherheit.brl.bloxberg.cache.ethereum;

public class ReducedTransactionObject {

    public final String fromAddress;
    public final String toAddress;

    public ReducedTransactionObject(BlockTransaction trans) {
        this.fromAddress = trans.getFromAddress();
        this.toAddress = trans.getToAddress();
    }

}
