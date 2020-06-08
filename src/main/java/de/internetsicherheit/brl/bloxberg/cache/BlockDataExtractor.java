package de.internetsicherheit.brl.bloxberg.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import de.internetsicherheit.brl.bloxberg.cache.ethereum.Block;
import de.internetsicherheit.brl.bloxberg.cache.ethereum.BlockTransaction;
import de.internetsicherheit.brl.bloxberg.cache.ethereum.BloxbergClient;
import de.internetsicherheit.brl.bloxberg.cache.ethereum.InformationForJson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class BlockDataExtractor {

    public static final String OUTPUTDIRECTORYNAME = System.getProperty("user.dir") + "/output/";
    private BloxbergClient client;
    private File outputdirectory;
    private File outputfile;
    private int start;
    private int stop;
    private String filename;

    public BlockDataExtractor(String[] args) {

        this.client = new BloxbergClient(args[0]);
        outputdirectory = new File(OUTPUTDIRECTORYNAME);
        if (!outputdirectory.exists()) {
            //noinspection ResultOfMethodCallIgnored
            outputdirectory.mkdir();
        }
        filename = args[1];
        this.outputfile = new File(OUTPUTDIRECTORYNAME + filename + ".json");
        this.start = Integer.parseInt(args[2]);
        this.stop = Integer.parseInt(args[3]);
    }

    public void generateJsonFile() throws IOException {
        //noinspection ResultOfMethodCallIgnored
        outputfile.delete();
        FileWriter fileWriter = new FileWriter(outputfile, false);
        ObjectMapper objectMapper = new ObjectMapper();
        SequenceWriter seqWriter = objectMapper.writer().writeValuesAsArray(fileWriter);
        for (; start <= stop; start++) {
            writeOutTransactions(start, seqWriter);
        }
        seqWriter.close();
    }

    public void writeOutTransactions(int blockNumber, SequenceWriter seqWriter) throws IOException {
        BigInteger blockNumberToBigInteger = BigInteger.valueOf(blockNumber);

        List<BlockTransaction> transactions =  client.getBlock(blockNumberToBigInteger).getTransactions();
        BigInteger timestamp = client.getBlock(blockNumberToBigInteger).getTimestamp();

        for (BlockTransaction transaction : transactions) {
            seqWriter.write(new InformationForJson(transaction, timestamp));
        }
    }
}
