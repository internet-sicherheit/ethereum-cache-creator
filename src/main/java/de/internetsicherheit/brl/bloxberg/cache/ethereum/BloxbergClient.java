package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
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
import org.web3j.protocol.websocket.WebSocketListener;
import org.web3j.protocol.websocket.WebSocketService;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
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
        String infura = "wss://mainnet.infura.io/ws/v3/18fb2fab0e8a4a588c66af7182314a7b";

        try {
            webSocketClient = new WebSocketClient(new URI(wssWebsockets));
            // infura:  Geth/v1.9.9-omnibus-e320ae4c-20191206/linux-amd64/go1.13.4
            // bloxberg: Parity-Ethereum//v2.6.6-beta-5162bc2-20191205/x86_64-linux-gnu/rustc1.39.0
           WebSocketListener wsl = new WebSocketListener() {
               @Override
               public void onMessage(String message) throws IOException {
                   System.out.println(message);
               }

               @Override
               public void onError(Exception e) {

               }

               @Override
               public void onClose() {

               }
           };
            webSocketClient.setListener( wsl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        boolean includeRawResponses = false;
        try {
            System.out.println(webSocketClient.connectBlocking());
            WebSocketService webSocketService = new WebSocketService(webSocketClient, true);
            String clientversion = getClientversion(webSocketService);
            System.out.println(clientversion);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("failed to get clientversion.");
            e.printStackTrace();
        }
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
                webSocketClient.reconnectBlocking();
                while(webSocketClient.isConnecting()) {
                    System.out.println("sleeping");
                    Thread.sleep(1000);
                }
                clientversion = getClientversion(webSocketService);
            } catch (ExecutionException e) {
                System.out.println("cause: " +e.getCause().toString());
                e.printStackTrace();
            }
            System.out.println("ClientVersion: " + clientversion);
            try {
                System.out.println("ClientVersion via web3j obejct: " + web3j2.web3ClientVersion().send());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("couldnt determine clientversion");
            }

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

    /**
     *
     * @param webSocketService
     * @return
     * @throws ExecutionException we usually get an exception like this one:
     * java.util.concurrent.ExecutionException: java.io.IOException: Request with id 6 timed out
     * @throws InterruptedException
     */
    private String getClientversion(WebSocketService webSocketService) throws ExecutionException, InterruptedException {
        System.out.println("enter method");
         Request<?, Web3ClientVersion> request = new Request<>(
                // Name of an RPC method to call
                "web3_clientVersion",
                // Parameters for the method. "web3_clientVersion" does not expect any
                Collections.<String>emptyList(),
                // Service that is used to send a request
                webSocketService,
                // Type of an RPC call to get an Ethereum client version
                Web3ClientVersion.class);
        System.out.println("request created");

// Send an asynchronous request via WebSocket protocol
        System.out.println("send request via websocket: " + request.toString());
         CompletableFuture<Web3ClientVersion> reply = webSocketService.sendAsync(
                request,
                Web3ClientVersion.class);
        System.out.println("request sent " + request.toString());

// Get result of the reply

        System.out.println("getting reply " + reply.toString() );
         return reply.get().getWeb3ClientVersion();

    }
}
