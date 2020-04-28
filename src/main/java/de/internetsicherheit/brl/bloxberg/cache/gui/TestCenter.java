package de.internetsicherheit.brl.bloxberg.cache.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.internetsicherheit.brl.bloxberg.cache.ethereum.BlockWithData;
import de.internetsicherheit.brl.bloxberg.cache.ethereum.BloxbergClient;
import de.internetsicherheit.brl.bloxberg.cache.persistence.EthereumWriter;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

public class TestCenter {

    private BloxbergClient client;
    private EthereumWriter writer;
    private final String ETHEREUM_NETWORK = "https://core.bloxberg.org";
    private final String OUTPUTDIRECTORY = System.getProperty("user.dir") + "/output/";

    public TestCenter(BloxbergClient client) {
        this.client = client;
    }

    public void extrahiereJsonObject(int blockNumber) {
        BigInteger blockBigInteger = BigInteger.valueOf(blockNumber);
        try {
            EthBlock.Block ethBlock = client.getEthBlock(blockBigInteger).getBlock();

            BlockWithData bwd = new BlockWithData(ethBlock);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(OUTPUTDIRECTORY + "extractedData6.json"), bwd);
        } catch (IOException e) {
            System.out.println("couldnt extract data: " + e);
        }
    }
}
