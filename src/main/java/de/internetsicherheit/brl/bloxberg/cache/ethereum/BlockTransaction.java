package de.internetsicherheit.brl.bloxberg.cache.ethereum;

public class BlockTransaction {

    public final TransactionAddress fromAddress;
    public final TransactionAddress toAddress;

    public BlockTransaction(TransactionAddress fromAddress, TransactionAddress toAddress) {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
    }

}
