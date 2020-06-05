package de.internetsicherheit.brl.bloxberg.cache.persistence;

import de.internetsicherheit.brl.bloxberg.cache.ethereum.Block;
import de.internetsicherheit.brl.bloxberg.cache.ethereum.BlockWithTransactionCombination;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class EthereumWriter {

    private Path outputFilePath;

    public EthereumWriter(Path dir, String filename) {
        outputFilePath = dir.resolve(filename);
    }

    /**
     * write a combination of blocknumber and transactioncount for that block into a file specified in the outputfilepath     *
     * @param bwtc the combination of blocknumber and transactioncount as a custom data type
     * @throws IOException exception when writing fails
     */
    public void writeBlockWithTransactions(BlockWithTransactionCombination bwtc) throws IOException {
        Files.writeString(outputFilePath,  bwtc.blockNumber + ","
                + bwtc.transactionCount + "\n", StandardOpenOption.APPEND, StandardOpenOption.CREATE);

    }
    public void writeBlockWithData(Block bwtc) throws IOException {
        Files.writeString(outputFilePath,  ","
                +"\n", StandardOpenOption.APPEND, StandardOpenOption.CREATE);

    }
}
