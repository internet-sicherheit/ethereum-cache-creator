package de.internetsicherheit.brl.bloxberg.cache.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.internetsicherheit.brl.bloxberg.cache.ethereum.BloxbergClient;
import de.internetsicherheit.brl.bloxberg.cache.ethereum.ReducedTransObjectArray;
import de.internetsicherheit.brl.bloxberg.cache.persistence.EthereumWriter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;

import java.io.File;
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

        //FileWriter fileWriter = new FileWriter(outputfile, true);
        ObjectMapper objectMapper = new ObjectMapper();
        ReducedTransObjectArray rdoa = new ReducedTransObjectArray();
        //SequenceWriter seqWriter = objectMapper.writer().writeValuesAsArray(fileWriter);
        for (int i = start; start <= stop; start ++) {
            extractJsonObject(start, rdoa);
        }
        //seqWriter.close();;
        objectMapper.writeValue(outputfile, rdoa);

    }

    public void extractJsonObject(int blockNumber, ReducedTransObjectArray rdoa) throws IOException {
        BigInteger blockBigInteger = BigInteger.valueOf(blockNumber);

        EthBlock.Block ethBlock = client.getEthBlock(blockBigInteger).getBlock();
        //BlockWithData bwd = new BlockWithData(ethBlock);

        List transList = ethBlock.getTransactions();
        ListIterator it = transList.listIterator();
        while (it.hasNext()) {
            //seqWriter.write(new ReducedTransObject((Transaction) it.next()));
            rdoa.addTransaction((Transaction) it.next());


        }
    }
}
