package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;

import java.util.List;

public class ReducedTransObject {

    public final long timestamp;
    public final String fromAddress;
    public final String toAddress;
    public final List uncles;

    public ReducedTransObject(Transaction trans, EthBlock.Block block) {

        this.fromAddress = trans.getFrom();

        this.toAddress = trans.getTo();

        this.timestamp = block.getTimestamp().longValue();
        this.uncles = block.getUncles();

    }

    public List getUncles() {
        return uncles;

    }
}
