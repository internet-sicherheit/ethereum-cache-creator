package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.web3j.protocol.core.methods.response.EthBlock;

import java.math.BigInteger;
import java.util.List;

public class BlockWithData {
    public List transactions;
    public List sealFields;
    public String author;
    public BigInteger difficulty;
    public String extraData;
    public BigInteger gasLimit;
    public BigInteger gasUsed;
    public String hash;
    public String logsBloom;
    public String miner;
    public int nonce;
    public BigInteger number;
    public String parentHash;
    public String receiptRoot;
    public BigInteger size;
    public List uncles;
    public BigInteger timestamp;
    /**
     * a simple custom Datatype that represents a combination of a blocknumber and the corresponding transactioncount
          */
    public BlockWithData(EthBlock.Block ethBlock) {
        this.transactions = ethBlock.getTransactions();
        this.sealFields = ethBlock.getSealFields();
        this.author = ethBlock.getAuthor();
        this.difficulty = ethBlock.getDifficulty();
        this.extraData = ethBlock.getExtraData();
        this.gasLimit = ethBlock.getGasLimit();
        this.gasUsed = ethBlock.getGasUsed();
        this.hash = ethBlock.getHash();
        this.logsBloom = ethBlock.getLogsBloom();
        this.miner = ethBlock.getMiner();
        //this.nonce = ethBlock.getNonce().intValue();
        this.number = ethBlock.getNumber();
        this.parentHash = ethBlock.getParentHash();
        this.receiptRoot = ethBlock.getReceiptsRoot();
        this.size = ethBlock.getSize();
        this.uncles = ethBlock.getUncles();
        this.timestamp = ethBlock.getTimestamp();
    }

}
