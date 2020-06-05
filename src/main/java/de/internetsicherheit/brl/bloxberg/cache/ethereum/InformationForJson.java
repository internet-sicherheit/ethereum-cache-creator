package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.web3j.protocol.core.methods.response.Transaction;

import java.math.BigInteger;

public class InformationForJson {

    public BigInteger timestamp;
    public String fromAddress;
    public String fromAddressLabel;
    public String toAddress;
    public String toAddressLabel;

    public InformationForJson(BlockTransaction blockTransaction,BigInteger timestamp ) {

        this.timestamp = timestamp;
        this.fromAddress = blockTransaction.fromAddress.address;
        this.fromAddressLabel = blockTransaction.fromAddress.label.toString();
        this.toAddress = blockTransaction.toAddress.address;
        this.toAddressLabel = blockTransaction.toAddress.label.toString();
    }
}
