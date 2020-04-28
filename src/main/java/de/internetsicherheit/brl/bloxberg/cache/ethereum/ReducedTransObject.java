package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.web3j.protocol.core.methods.response.Transaction;

public class ReducedTransObject {

    /**
     * a simple custom Datatype that represents a combination of a blocknumber and the corresponding transactioncount
          */
    public String fromAddress;
    public String toAddress;

    public ReducedTransObject(Transaction trans) {

        this.fromAddress = trans.getFrom();


            this.toAddress = trans.getTo();


    }


}
