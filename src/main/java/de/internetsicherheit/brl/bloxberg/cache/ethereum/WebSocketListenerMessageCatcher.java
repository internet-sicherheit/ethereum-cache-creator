package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.web3j.protocol.websocket.WebSocketListener;

import java.io.IOException;

/**
 * this class listens to the WebsocketClient and triggers its methods whenever a request is sent.
 *
 */
public class WebSocketListenerMessageCatcher implements WebSocketListener {

    String currentMessage;
    public WebSocketListenerMessageCatcher() {
        this.currentMessage = "";
    }
    /**
     * Some requests trigger messages, others do not. In this method we can handle these messages.
     * @param message the message sent by the WebSocketClient
     * @throws IOException
     */
    @Override
    public void onMessage(String message) throws IOException {
        currentMessage = message;
        System.out.println(currentMessage);

    }

    @Override
    public void onError(Exception e) {
        System.out.println(e.getCause());

    }

    @Override
    public void onClose() {
        System.out.println("WebSocketClient closed.");
    }
    public String giveCurrentMessage() {
        return currentMessage;
    }
}
