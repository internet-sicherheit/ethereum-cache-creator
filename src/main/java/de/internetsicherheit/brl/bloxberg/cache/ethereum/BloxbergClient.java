package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetBlockTransactionCountByNumber;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.websocket.WebSocketClient;
import org.web3j.protocol.websocket.WebSocketService;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;

public class BloxbergClient {

    private final Web3j web3j;
    private Web3j web3j2;

    /**
     * the bloxbergclient. any other blockchain can be used aswell
     * @param networkUrl the url of the blockchain
     */
    public BloxbergClient(String networkUrl) {
        web3j = Web3j.build(new HttpService(networkUrl));
        //WebSocketService wss = new WebSocketService("wss://core.bloxberg.org", false);

        WebSocketClient webSocketClient = null;
        try {
            webSocketClient = new WebSocketClient(new URI("ws://core.bloxberg.org"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        boolean includeRawResponses = false;
        WebSocketService webSocketService = new WebSocketService(webSocketClient, includeRawResponses);
        try {
            webSocketClient.connectBlocking();
            System.out.println("websocket socket: " + webSocketClient.getSocket());
            System.out.println("websocket is open: " + webSocketClient.isOpen());
            web3j2 = Web3j.build(webSocketService);
            System.out.println("successfully build Web3j object.");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("failed to build Web3j object.");
        }


    }

    public BigInteger getCurrentBlockNumber() throws IOException {

        EthBlockNumber blockNumber = web3j2.ethBlockNumber().send();


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
    public EthBlock getEthBlock(BigInteger block) throws IOException {

        return web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(block), true).send();

    }
}
