package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.web3j.protocol.websocket.WebSocketListener;

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
     */
    @Override
    public void onMessage(String message) {
        currentMessage = message;
        System.out.println("WebSocketListenerMessageCatcher.currentMessage: " + currentMessage);

    }

    @Override
    public void onError(Exception e) {
        e.getCause();

    }

    @Override
    public void onClose() {
        System.out.println("WebSocketClient closed.");
    }
    public String giveCurrentMessage() {
        return currentMessage;
    }
}
