package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.web3j.protocol.core.methods.response.Transaction;

public class BlockTransaction {

    public String fromAddress;
    public String toAddress;

    public BlockTransaction(Transaction transaction) {
        this.fromAddress = transaction.getFrom();
        this.toAddress = transaction.getTo();
    }

}
