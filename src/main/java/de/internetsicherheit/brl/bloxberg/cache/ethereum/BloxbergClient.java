package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.websocket.WebSocketClient;
import org.web3j.protocol.websocket.WebSocketListener;
import org.web3j.protocol.websocket.WebSocketService;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;

public class BloxbergClient {

    private final Web3j web3j;
    private EthBlock rawBlock;
    private BlockWithData blockWithData;


    /**
     * the bloxbergclient. any other blockchain can be used aswell
     *
     * @param networkUrl the url of the blockchain
     */
    public BloxbergClient(String networkUrl) {
        web3j = Web3j.build(new HttpService(networkUrl));

        try {
            Web3j web3jwss = buildWssClient();
            System.out.println("successfully built Web3j object with websocket.");
            getSomeTestData(web3jwss);
        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }
    public Web3j buildWssClient() throws URISyntaxException, InterruptedException {
        WebSocketClient webSocketClient = null;
        String wssBloxberg = "wss://websockets.bloxberg.org/";
        String wssInfura = "wss://mainnet.infura.io/ws/v3/18fb2fab0e8a4a588c66af7182314a7b";

            webSocketClient = new WebSocketClient(new URI(wssBloxberg));
            WebSocketListener wsl = new WebSocketListener() {

                @Override
                public void onMessage(String message) throws IOException {
                    /**
                     * some requests trigger messages others do not.
                     */
                    System.out.println(message);
                }

                @Override
                public void onError(Exception e) {
                    System.out.println(e.getCause());

                }

                @Override
                public void onClose() {
                    System.out.println("WebSocketClient closed.");
                }
            };
            webSocketClient.setListener(wsl);

            System.out.println("websocket connected: " + webSocketClient.connectBlocking());
            System.out.println("websocket socket: " + webSocketClient.getSocket());
            System.out.println("websocket Uri: " + webSocketClient.getURI());
            System.out.println("websocket is open: " + webSocketClient.isOpen());
            boolean includeRawResponses = true;
            WebSocketService webSocketService = new WebSocketService(webSocketClient, includeRawResponses);

            return Web3j.build(webSocketService);

    }

    public BigInteger getCurrentBlockNumber() throws IOException {
        EthBlockNumber blockNumber = web3j.ethBlockNumber().send();
        return blockNumber.getBlockNumber();

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
    public void getSomeTestData(Web3j web3jwss) throws IOException {
        Request<?, Web3ClientVersion> request1 = web3jwss.web3ClientVersion();

        Request<?, EthGetBlockTransactionCountByNumber> request2 =
                web3j.ethGetBlockTransactionCountByNumber(DefaultBlockParameter.valueOf(BigInteger.valueOf(34)));
        Request<?, EthBlockNumber> request3 = web3jwss.ethBlockNumber();

        request1.sendAsync();
        // request2 does not trigger a message
        request2.sendAsync();
        request3.sendAsync();
    }
}

