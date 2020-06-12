package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.codehaus.httpcache4j.uri.URIBuilder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.BatchRequest;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.websocket.WebSocketClient;
import org.web3j.protocol.websocket.WebSocketService;

import java.io.FileWriter;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;

public class EthereumWssClient {


    private WebSocketService webSocketService;
    WebSocketMessageCatcher listener;
    private Web3j web3jwss;
    private EthBlock rawBlock;
    private BlockWithData blockWithData;


    /**
     * the bloxbergclient. any other blockchain can be used aswell
     *
     * @param networkWssUri the url of the blockchain
     */
    public EthereumWssClient(String networkWssUri, FileWriter fileWriter) throws URISyntaxException, InterruptedException {
        listener = new WebSocketMessageCatcher(fileWriter);
        webSocketService = buildWssService(listener, networkWssUri);
        web3jwss = Web3j.build(webSocketService);

    }

    public WebSocketService buildWssService(WebSocketMessageCatcher wsl, String networkWssUri) throws URISyntaxException, InterruptedException {

        WebSocketClient webSocketClient;

        URI uriWithoutPort = new URI(networkWssUri);

        // building the uri with this port does not work (websocketNotConnectedException).
        // we will continue to work with the default port (443) until we have figured out
        // what's behind this issue.
        URI uriWithPort = URIBuilder.fromURI(uriWithoutPort).withPort(8545).toURI();

        webSocketClient = new WebSocketClient(uriWithoutPort);

        webSocketClient.setListener(wsl);

        System.out.println("websocket connected: " + webSocketClient.connectBlocking());
        System.out.println("websocket socket: " + webSocketClient.getSocket());
        System.out.println("websocket Uri: " + webSocketClient.getURI());
        System.out.println("websocket is open: " + webSocketClient.isOpen());

        return new WebSocketService(webSocketClient, false);

    }

    public void sendBatchRequest(int start, int limit) throws InterruptedException {
        listener.setLatch(1);
        BatchRequest batchRequest = new BatchRequest(webSocketService);
        for (; start < limit; start++) {

            batchRequest.add(web3jwss.ethGetBlockByNumber(DefaultBlockParameter.valueOf(BigInteger.valueOf(start)), false));
        }
        webSocketService.sendBatchAsync(batchRequest);
        listener.waitForLatch();
        System.out.println("all responses sent.");
    }
}


