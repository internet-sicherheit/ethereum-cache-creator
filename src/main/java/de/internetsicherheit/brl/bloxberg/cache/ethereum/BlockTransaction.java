package de.internetsicherheit.brl.bloxberg.cache.ethereum;

public class BlockTransaction {

    public TransactionAddress fromAddress;
    public TransactionAddress toAddress;

    public BlockTransaction(TransactionAddress fromAddress, TransactionAddress toAddress) {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
    }

}
