package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.web3j.protocol.core.methods.response.Transaction;

public class BlockTransaction {

    public TransactionAddress fromAddress;
    public TransactionAddress toAddress;

    public BlockTransaction(Transaction transaction) {
        this.fromAddress = new TransactionAddress(transaction.getFrom());
        this.toAddress = new TransactionAddress(transaction.getTo());
    }

}
