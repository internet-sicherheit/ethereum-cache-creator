package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.web3j.protocol.core.methods.response.Transaction;

import java.math.BigInteger;

public class InformationForJson {

    public BigInteger timestamp;
    public String fromAddress;
    public String toAddress;

    public InformationForJson(BlockTransaction blockTransaction,BigInteger timestamp ) {

        this.timestamp = timestamp;
        this.fromAddress = blockTransaction.fromAddress.address;
        this.toAddress = blockTransaction.toAddress.address;
    }


}
