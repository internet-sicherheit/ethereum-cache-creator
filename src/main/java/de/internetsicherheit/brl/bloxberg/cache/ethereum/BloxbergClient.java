package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.codehaus.httpcache4j.uri.URIBuilder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetBlockTransactionCountByNumber;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.websocket.WebSocketClient;
import org.web3j.protocol.websocket.WebSocketService;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

public class BloxbergClient {

    private final Web3j web3j;

    private Web3j web3jwss;
    private EthBlock rawBlock;
    private BlockWithData blockWithData;


    /**
     * the bloxbergclient. any other blockchain can be used aswell
     *
     * @param networkUrl the url of the blockchain
     */
    public BloxbergClient(String networkUrl) {
        web3j = Web3j.build(new HttpService(networkUrl));

    }

    /**
     * sends a request to the blockchain to extract the number of transactions in a single block.
     * this method is quite timeconsuming.
     *
     * @param block the blocknumber
     * @return the transactioncount
     * @throws IOException connection to client lost/cannot be established
     */

    public int getNumberOfTransactionsInBlock(BigInteger block) throws IOException {
        Request<?, EthGetBlockTransactionCountByNumber> request = web3j.ethGetBlockTransactionCountByNumber(DefaultBlockParameter.valueOf(block));
        EthGetBlockTransactionCountByNumber transactionCountByNumber = request.send();
        return transactionCountByNumber.getTransactionCount().intValue();
    }

    private EthBlock getEthBlock(BigInteger block) throws IOException {
        return web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(block), true).send();
    }

    public BlockWithData getBlockWithData(BigInteger block) throws IOException {
        this.rawBlock = this.getEthBlock(block);
        this.blockWithData = new BlockWithData(this.rawBlock.getBlock());
        return this.blockWithData;
    }

    public BigInteger getBlockTimestamp(BigInteger block) throws IOException {
        BlockWithData blockForTimestamp = this.getBlockWithData(block);
        return blockForTimestamp.getTimestamp();
    }
    public BigInteger getCurrentBlockNumber() throws IOException {
        EthBlockNumber blockNumber = web3j.ethBlockNumber().send();
        return blockNumber.getBlockNumber();

    }
    public Web3j buildWssClient(WebSocketListenerMessageCatcher wsl) throws URISyntaxException, InterruptedException {

        WebSocketClient webSocketClient;
        String wssBloxberg = "wss://websockets.bloxberg.org/";
        String wssInfura = "wss://mainnet.infura.io/ws/v3/18fb2fab0e8a4a588c66af7182314a7b";

        URI uriWithoutPort = new URI (wssBloxberg);

        // does not work
        URI uriWithPort = URIBuilder.fromURI(uriWithoutPort).withPort(8545).toURI();

        webSocketClient = new WebSocketClient(uriWithoutPort);


        webSocketClient.setListener(wsl);

        System.out.println("websocket connected: " + webSocketClient.connectBlocking());
        System.out.println("websocket socket: " + webSocketClient.getSocket());
        System.out.println("websocket Uri: " + webSocketClient.getURI());
        System.out.println("websocket is open: " + webSocketClient.isOpen());

        boolean includeRawResponses = false;
        WebSocketService webSocketService = new WebSocketService(webSocketClient, includeRawResponses);

        return Web3j.build(webSocketService);

    }


    public void getSomeWssTestData() throws URISyntaxException, InterruptedException {
        WebSocketListenerMessageCatcher wsl = new WebSocketListenerMessageCatcher();
        web3jwss = buildWssClient(wsl);

        int numberOfMessages = 1000;
        wsl.setLatch(numberOfMessages);
        long startWss = System.currentTimeMillis();
        for (int i = 0; i < numberOfMessages; i++) {
            // this gives us the whole transaction data we used to have as a String
            CompletableFuture future = web3jwss.ethGetBlockByNumber(DefaultBlockParameter.valueOf(BigInteger.valueOf(i)), false)
                    .sendAsync();
        }
        wsl.waitForLatch();
        long stopWss = System.currentTimeMillis();
        long wssDuration = stopWss - startWss;
        System.out.println(String.format("WS connection took %d ms for %d requests", wssDuration, numberOfMessages));

        long httpStart = System.currentTimeMillis();
        for(int j = 0; j < 1000; j++) {
            try {
                getEthBlock(BigInteger.valueOf(j));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long httpEnd = System.currentTimeMillis();
        long httpDuration = httpEnd - httpStart;
        System.out.println(String.format("HTTP connection took %d ms for %d requests", httpDuration, numberOfMessages));
    }
}

