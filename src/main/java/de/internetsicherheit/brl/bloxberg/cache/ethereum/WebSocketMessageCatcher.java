package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.websocket.WebSocketListener;

import java.io.FileWriter;
import java.util.concurrent.CountDownLatch;

/**
 * this class listens to the WebsocketClient and triggers its methods whenever a request is sent.
 *
 */
public class WebSocketMessageCatcher implements WebSocketListener {

    String currentMessage;

    ObjectMapper objectMapper = new ObjectMapper();
    FileWriter fileWriter;
    EthBlock[] ethBlock;

    private CountDownLatch latch;
    public WebSocketMessageCatcher(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }


    /**
     * Some requests trigger messages, others do not. In this method we can handle these messages.
     * @param message the message sent by the WebSocketClient
     */
    @Override
    public void onMessage(String message) {
        currentMessage = message;
        System.out.println("currentmessage: " + currentMessage);
        latch.countDown();
        try {
            ethBlock = objectMapper.readValue(currentMessage, EthBlock[].class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public void setLatch(int i) {
        latch = new CountDownLatch(i);
    }

    public void waitForLatch() throws InterruptedException {
        latch.await();
    }

    @Override
    public void onError(Exception e) {
        e.getCause();

    }

    @Override
    public void onClose() {
        System.out.println("WebSocketClient closed.");
    }
}
