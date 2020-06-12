package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class EthereumWssClientTest {
    @DisplayName("sending a batch request")
    @Test
    void testBatchRequest() throws URISyntaxException, InterruptedException, IOException {
        EthereumWssClient wssClient = buildClient();
        wssClient.sendBatchRequest(0, 1000);
        assert true;
    }

    private EthereumWssClient buildClient() throws URISyntaxException, InterruptedException, IOException {
        return new EthereumWssClient("wss://websockets.bloxberg.org/",
                new FileWriter(new File(System.getProperty("user.dir")
                        + "/output/" +"wss_test_file.json"), false));
    }
}

