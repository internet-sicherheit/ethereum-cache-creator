package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.web3j.protocol.core.methods.response.Transaction;

import java.math.BigInteger;

public class InformationForJson {

    public BigInteger timestamp;
    public String fromAddress;
    public final String toAddress;

    public InformationForJson(BlockTransaction blockTransaction,BigInteger timestamp ) {

        this.timestamp = timestamp;
        this.fromAddress = blockTransaction.fromAddress;
        this.toAddress = blockTransaction.toAddress;


    }


}
