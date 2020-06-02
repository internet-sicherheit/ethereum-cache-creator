package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class BlockWithData {
    private List transactions;
    private List sealFields;
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
    private List uncles;
    private BigInteger timestamp;

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

    public List<BlockTransaction> getTransactions() {
        List<BlockTransaction> blockTransactions = new ArrayList<BlockTransaction>();

        ListIterator it = transactions.listIterator();

        while (it.hasNext()) {
            blockTransactions.add(new BlockTransaction((Transaction) it.next()));
        }
        return blockTransactions;
    }
}
