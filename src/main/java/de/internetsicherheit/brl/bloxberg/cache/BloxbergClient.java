package de.internetsicherheit.brl.bloxberg.cache;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetBlockTransactionCountByNumber;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

public class BloxbergClient {

    private final Web3j web3j;

    /**
     * the bloxbergclient. any other blockchain can be used aswell
     * @param networkUrl the url of the blockchain
     */
    public BloxbergClient(String networkUrl) {
        web3j = Web3j.build(new HttpService(networkUrl));
    }

    public BigInteger getCurrentBlockNumber() throws IOException {

        EthBlockNumber blockNumber = web3j.ethBlockNumber().send();
        return blockNumber.getBlockNumber();
    }

    /**
     * sends a request to the blockchain to extract the number of transactions in a single block.
     * this method is quite timeconsuming.
     * @param block the blocknumber
     * @return the transactioncount
     * @throws IOException connection to client lost/cannot be established
     */
    public int getNumberOfTransactionsInBlock(BigInteger block) throws IOException {

        Request<?, EthGetBlockTransactionCountByNumber> request = web3j.ethGetBlockTransactionCountByNumber(DefaultBlockParameter.valueOf(block));
        EthGetBlockTransactionCountByNumber transactionCountByNumber = request.send();
        return transactionCountByNumber.getTransactionCount().intValue();
    }
}
