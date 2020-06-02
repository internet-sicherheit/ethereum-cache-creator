package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.web3j.protocol.core.methods.response.Transaction;

public class ReducedTransactionObject {

    public final String fromAddress;
    public final String toAddress;

    public ReducedTransactionObject(Transaction trans) {
        this.fromAddress = trans.getFrom();
        this.toAddress = trans.getTo();
    }

}
