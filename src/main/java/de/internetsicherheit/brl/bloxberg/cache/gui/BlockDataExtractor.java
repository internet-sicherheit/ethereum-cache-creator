package de.internetsicherheit.brl.bloxberg.cache.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import de.internetsicherheit.brl.bloxberg.cache.ethereum.BlockTransaction;
import de.internetsicherheit.brl.bloxberg.cache.ethereum.BloxbergClient;
import de.internetsicherheit.brl.bloxberg.cache.ethereum.ReducedTransactionObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.ListIterator;

public class BlockDataExtractor {

    private BloxbergClient client;
    private File outputfile;
    private int start;
    private int stop;
    private String filename;

    public BlockDataExtractor(String[] args) throws IOException {
        this.client = new BloxbergClient(args[0]);
        String OUTPUTDIRECTORYNAME = System.getProperty("user.dir") + "/output/";
        File outputdirectory = new File(OUTPUTDIRECTORYNAME);
        if (!outputdirectory.exists()) {
            outputdirectory.mkdir();
        }
        filename = args[1];
        this.outputfile = new File(OUTPUTDIRECTORYNAME + filename + ".json");
        this.start = Integer.parseInt(args[2]);
        this.stop = Integer.parseInt(args[3]);
    }

    public void generateJsonFile() throws IOException {
        outputfile.delete();
        FileWriter fileWriter = new FileWriter(outputfile, false);
        ObjectMapper objectMapper = new ObjectMapper();
        SequenceWriter seqWriter = objectMapper.writer().writeValuesAsArray(fileWriter);
        for (int i = start; start <= stop; start++) {
            writeOutTransactions(start, seqWriter);
        }
        seqWriter.close();
    }

    public void writeOutTransactions(int blockNumber, SequenceWriter seqWriter) throws IOException {
        BigInteger blockBigInteger = BigInteger.valueOf(blockNumber);
        List transactions = client.getBlockWithData(blockBigInteger).getTransactions();

        ListIterator it = transactions.listIterator();
        while (it.hasNext()) {
            seqWriter.write(new ReducedTransactionObject((BlockTransaction) it.next()));
        }
    }
}
