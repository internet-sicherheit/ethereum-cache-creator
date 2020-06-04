package de.internetsicherheit.brl.bloxberg.cache.ethereum;

public class AddressInformationForJson {

    public final String fromAddress;
    public final String toAddress;

    public AddressInformationForJson(BlockTransaction blockTransaction) {
        this.fromAddress = blockTransaction.getFromAddress();
        this.toAddress = blockTransaction.getToAddress();
    }

}
