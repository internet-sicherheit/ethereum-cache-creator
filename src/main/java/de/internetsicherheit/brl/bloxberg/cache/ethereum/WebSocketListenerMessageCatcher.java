package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.websocket.WebSocketListener;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * this class listens to the WebsocketClient and triggers its methods whenever a request is sent.
 *
 */
public class WebSocketListenerMessageCatcher implements WebSocketListener {

    String currentMessage;
    Timestamp currentTs;

    ObjectMapper objectMapper = new ObjectMapper();

    EthBlock ethBlock;

    private CountDownLatch latch;



    /**
     * Some requests trigger messages, others do not. In this method we can handle these messages.
     * @param message the message sent by the WebSocketClient
     */
    @Override
    public void onMessage(String message) {
        currentMessage = message;
        latch.countDown();
        try {
            Date date = new Date();
            ethBlock = objectMapper.readValue(currentMessage, EthBlock.class);
            currentTs = new Timestamp(date.getTime());
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
