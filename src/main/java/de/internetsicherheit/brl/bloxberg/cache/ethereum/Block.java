package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.protocol.core.methods.response.Transaction;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class Block {
    private List<BlockTransaction> blockTransactions;

    @SuppressWarnings("rawtypes") // we get the raw type from web3j like this
    private List<TransactionResult> transactions;

    private List<String> sealFields;
    private String author;
    private BigInteger difficulty;
    private String extraData;
    private BigInteger gasLimit;
    private BigInteger gasUsed;
    private String hash;
    private String logsBloom;
    private String miner;
    private int nonce;
    private BigInteger number;
    private String parentHash;
    private String receiptRoot;
    private BigInteger size;
    private List<String> uncles;
    private BigInteger timestamp;

    /**
     * a simple custom Datatype that represents a combination of a blocknumber and the corresponding transactioncount
     */
    public Block(EthBlock.Block ethBlock) {
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
        this.blockTransactions = this.transformTransactions();

    }

    private List<BlockTransaction> transformTransactions() {
        return this.transactions.stream()
                .map(t -> new BlockTransaction((Transaction) t))
                .collect(Collectors.toList());
    }

    public void labelTransactions(List<BlockTransaction> labelledTransactions){
        this.blockTransactions = labelledTransactions;
    }

    public List<BlockTransaction> getTransactions() {

        return this.blockTransactions;
    }

    public BigInteger getTimestamp() {

        return this.timestamp;
    }

}
