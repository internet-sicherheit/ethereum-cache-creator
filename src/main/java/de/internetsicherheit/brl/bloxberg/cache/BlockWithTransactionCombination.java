package de.internetsicherheit.brl.bloxberg.cache;

import java.math.BigInteger;

public class BlockWithTransactionCombination {
    public BigInteger blockNumber;
    public int transactionCount;
    /**
     * a simple custom Datatype that represents a combination of a blocknumber and the corresponding transactioncount
     * @param blockNumber the blocknumber of the block
     * @param transactionCount the amount of transactions withing the block
     */
    public BlockWithTransactionCombination(BigInteger blockNumber, int transactionCount) {
        this.blockNumber = blockNumber;
        this.transactionCount = transactionCount;
    }

}
