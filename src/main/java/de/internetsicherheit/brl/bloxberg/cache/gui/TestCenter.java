package de.internetsicherheit.brl.bloxberg.cache.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import de.internetsicherheit.brl.bloxberg.cache.ethereum.BloxbergClient;
import de.internetsicherheit.brl.bloxberg.cache.ethereum.ReducedTransObject;
import de.internetsicherheit.brl.bloxberg.cache.persistence.EthereumWriter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.ListIterator;

public class TestCenter {

    private BloxbergClient client;
    private EthereumWriter writer;
    private final String ETHEREUM_NETWORK = "https://core.bloxberg.org";
    private final String OUTPUTDIRECTORY = System.getProperty("user.dir") + "/output/";
    private File outputfile;

    public TestCenter(BloxbergClient client, File outputfile) {
        this.client = client;
        this.outputfile = outputfile;


    }

    public void generateJsonFile(int start, int stop) throws IOException {

        FileWriter fileWriter = new FileWriter(outputfile, true);
        ObjectMapper objectMapper = new ObjectMapper();
        
        SequenceWriter seqWriter = objectMapper.writer().writeValuesAsArray(fileWriter);
        for (int i = start; start <= stop; start ++) {
            writeOutTransactions(start, seqWriter);
        }
        seqWriter.close();
        
    }

    public void writeOutTransactions(int blockNumber, SequenceWriter seqWriter) throws IOException {
        
        BigInteger blockBigInteger = BigInteger.valueOf(blockNumber);
        EthBlock.Block ethBlock = client.getEthBlock(blockBigInteger).getBlock();       
        List transList = ethBlock.getTransactions();
        ListIterator it = transList.listIterator();
        
        while (it.hasNext()) {
            seqWriter.write(new ReducedTransObject((Transaction) it.next()));            
        }
    }
}
