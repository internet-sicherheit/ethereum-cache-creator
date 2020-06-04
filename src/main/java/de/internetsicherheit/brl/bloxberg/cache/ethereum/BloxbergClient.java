package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.java_websocket.server.WebSocketServer;
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
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
        String localParity = "ws://localhost:8546/";
        String wssWebsockets = "wss://websockets.bloxberg.org/";
        String httpsWebsockets = "https://websockets.bloxberg.org/";
        String wsCore = "ws://core.bloxberg.org";

        try {
            webSocketClient = new WebSocketClient(new URI(wssWebsockets));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        boolean includeRawResponses = false;
        WebSocketService webSocketService = new WebSocketService(webSocketClient, includeRawResponses);
        try {
            System.out.println("websocket connected: " + webSocketClient.connectBlocking());

            System.out.println("websocket socket: " + webSocketClient.getSocket());
            System.out.println("websocket port: " + webSocketClient.getSocket().getPort());
            System.out.println("websocket Uri: " + webSocketClient.getURI());

            System.out.println("websocket is open: " + webSocketClient.isOpen());
            web3j2 = Web3j.build(webSocketService);
            System.out.println("successfully built Web3j object.");
            String clientversion = "noclientVersion";
            try {
                clientversion =
                clientversion(webSocketService);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println("ClientVersion: " + clientversion);
            System.out.println("ClientVersion vis web3j obejct: " + web3j2.web3ClientVersion());


        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("failed to build Web3j object.");
        }

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
    public EthBlock getEthBlock(BigInteger block) throws IOException {

        return web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(block), true).send();

    }

    private String clientversion(WebSocketService webSocketService) throws ExecutionException, InterruptedException {
         Request<?, Web3ClientVersion> request = new Request<>(
                // Name of an RPC method to call
                "web3_clientVersion",
                // Parameters for the method. "web3_clientVersion" does not expect any
                Collections.<String>emptyList(),
                // Service that is used to send a request
                webSocketService,
                // Type of an RPC call to get an Ethereum client version
                Web3ClientVersion.class);

// Send an asynchronous request via WebSocket protocol
         CompletableFuture<Web3ClientVersion> reply = webSocketService.sendAsync(
                request,
                Web3ClientVersion.class);

// Get result of the reply
         return reply.get().getWeb3ClientVersion();

    }
}
