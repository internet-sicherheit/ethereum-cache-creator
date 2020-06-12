package de.internetsicherheit.brl.bloxberg.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import de.internetsicherheit.brl.bloxberg.cache.ethereum.BlockTransaction;
import de.internetsicherheit.brl.bloxberg.cache.ethereum.BloxbergClient;
import de.internetsicherheit.brl.bloxberg.cache.ethereum.EthereumWssClient;
import de.internetsicherheit.brl.bloxberg.cache.ethereum.InformationForJson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.List;

public class BlockDataExtractor {

    public static final String OUTPUTDIRECTORYNAME = System.getProperty("user.dir") + "/output/";
    private BloxbergClient client;
    private EthereumWssClient wssClient;
    private File outputdirectory;
    private File outputfile;
    private int start;
    private int stop;
    private String filename;
    private final String wssURI;

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
        this.wssURI = args[5];

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
    // doesn't actually generate Json-File atm
    public void generateJsonFileViaWss() throws IOException, InterruptedException {
        //noinspection ResultOfMethodCallIgnored
        outputfile.delete();
        FileWriter fileWriter = new FileWriter(outputfile, false);
        try {
            this.wssClient = new EthereumWssClient(wssURI, fileWriter);
            System.out.println("wss client successfully built.");
            //wssClient.sendBatchRequest(0, 1000);
        } catch (URISyntaxException | InterruptedException e) {
            System.out.println("Could not build wss Client.");
            e.printStackTrace();
        }
        wssClient.sendBatchRequest(0, 1000);

        /*ObjectMapper objectMapper = new ObjectMapper();
        SequenceWriter seqWriter = objectMapper.writer().writeValuesAsArray(fileWriter);
        for (; start <= stop; start++) {
            writeOutTransactions(start, seqWriter);
        }
        seqWriter.close();*/

    }
    public void writeOutTransactionsFromListener(int blockNumber, SequenceWriter seqWriter) throws IOException {
        BigInteger blockBigInteger = BigInteger.valueOf(blockNumber);
        List<BlockTransaction> transactions = client.getBlockWithData(blockBigInteger).getTransactions();
        BigInteger timestamp = client.getBlockTimestamp(blockBigInteger);

        for (BlockTransaction transaction : transactions) {
            seqWriter.write(new InformationForJson(transaction, timestamp));
        }
    }

    public void writeOutTransactions(int blockNumber, SequenceWriter seqWriter) throws IOException {
        BigInteger blockBigInteger = BigInteger.valueOf(blockNumber);
        List<BlockTransaction> transactions = client.getBlockWithData(blockBigInteger).getTransactions();
        BigInteger timestamp = client.getBlockTimestamp(blockBigInteger);

        for (BlockTransaction transaction : transactions) {
            seqWriter.write(new InformationForJson(transaction, timestamp));
        }
    }
}
