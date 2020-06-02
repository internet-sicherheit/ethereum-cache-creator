package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.web3j.protocol.core.methods.response.Transaction;

public class BlockTransaction {

    private String fromAddress;
    private String toAddress;

    public BlockTransaction(Transaction transaction) {
        this.fromAddress = transaction.getFrom();
        this.toAddress = transaction.getTo();
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }
}
