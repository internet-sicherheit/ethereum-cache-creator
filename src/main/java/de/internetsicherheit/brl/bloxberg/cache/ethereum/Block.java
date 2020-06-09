package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.web3j.protocol.core.methods.response.EthBlock;

import java.math.BigInteger;
import java.util.List;

public class Block {
    private final List<BlockTransaction> blockTransactions;

    private final List<String> sealFields;
    private final String author;
    private final BigInteger difficulty;
    private final String extraData;
    private final BigInteger gasLimit;
    private final BigInteger gasUsed;
    private final String hash;
    private final String logsBloom;
    private final String miner;
    private final BigInteger number;
    private final String parentHash;
    private final String receiptRoot;
    private final BigInteger size;
    private final List<String> uncles;
    private final BigInteger timestamp;

    /**
     * a simple custom Datatype that represents a combination of a blocknumber and the corresponding transactioncount
     */
    public Block(EthBlock.Block ethBlock, List<BlockTransaction> blockTransactions) {
        this.blockTransactions = blockTransactions;
        this.sealFields = ethBlock.getSealFields();
        this.author = ethBlock.getAuthor();
        this.difficulty = ethBlock.getDifficulty();
        this.extraData = ethBlock.getExtraData();
        this.gasLimit = ethBlock.getGasLimit();
        this.gasUsed = ethBlock.getGasUsed();
        this.hash = ethBlock.getHash();
        this.logsBloom = ethBlock.getLogsBloom();
        this.miner = ethBlock.getMiner();
        this.number = ethBlock.getNumber();
        this.parentHash = ethBlock.getParentHash();
        this.receiptRoot = ethBlock.getReceiptsRoot();
        this.size = ethBlock.getSize();
        this.uncles = ethBlock.getUncles();
        this.timestamp = ethBlock.getTimestamp();
    }

    public List<BlockTransaction> getTransactions() {
        return this.blockTransactions;
    }

    public BigInteger getTimestamp() {
        return this.timestamp;
    }

}
