package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.web3j.protocol.core.methods.response.Transaction;

import java.util.ArrayList;

public class ReducedTransObjectArray {

    /**
     * not being used atm. ignore for now
     */
    public ArrayList<String> fromAddress;
    public ArrayList<String> toAddress;

    public ReducedTransObjectArray() {

        this.fromAddress = new ArrayList<String>();
        this.toAddress = new ArrayList<String>();

    }
    public void addTransaction(Transaction transaction) {

        fromAddress.add(transaction.getFrom());
        toAddress.add(transaction.getTo());
    }



}
