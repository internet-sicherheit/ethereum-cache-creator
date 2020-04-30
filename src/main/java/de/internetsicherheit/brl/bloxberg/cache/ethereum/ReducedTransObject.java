package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.web3j.protocol.core.methods.response.Transaction;

public class ReducedTransObject {


    public final String fromAddress;
    public final String toAddress;

    public ReducedTransObject(Transaction trans) {

        this.fromAddress = trans.getFrom();

        this.toAddress = trans.getTo();

    }


}
