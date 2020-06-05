package de.internetsicherheit.brl.bloxberg.cache.persistence;

import de.internetsicherheit.brl.bloxberg.cache.ethereum.BlockWithTransactionCombination;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class CacheFileReader {
    private final Path inputFilePath;
    public int lineCount;

    /**
     * A Reader that can read data from a file written by the EthereumWriter
     * @param inputFilePath the path of the file.
     */
    public CacheFileReader(Path inputFilePath) {
        this.inputFilePath = inputFilePath;
        this.lineCount = 0;
        try {
            this.lineCount = (int)countLines();
        } catch (IOException e) {
        }

    }
    public long countLines() throws IOException{
        return Files.lines(inputFilePath).count();
    }

    /**
     * reads all lines from the file specified in the inputFilePath
     * @return a Stream of parsed lines
     * @throws IOException exception when file cannot be read
     */
    public Stream<BlockWithTransactionCombination> readAllLines() throws IOException {
        return Files.lines(inputFilePath)
                .map(this::parseLine);
    }

    /**
     * this method is NOT USED in the current implementation of the programm
     * reads all lines from start (including) to end (excluding)
     * example: start = 0; end = 100 => Block in [0,100)
     * @param start first line to be read
     * @param end number of lines to be read
     * @return stream of BlockWithTransactionCombinations
     * @throws IOException file cannot be read
     */
    public Stream<BlockWithTransactionCombination> readLines(int start, int end) throws IOException {
        return Files.lines(inputFilePath)
                .skip(start)
                .map(this::parseLine)
                .limit(end);
    }


    /**
     * this method is being USED in the current implementation of the programm
     * reads all lines, parses them into BlockWithTransactionCombinations, and fills an array with those
     * @param start first line to be read
     * @param end number of lines read
     * @return array of BlockWithTransactionCombinations
     * @throws IOException file cannot be read
     */
    public BlockWithTransactionCombination[] readLinesAndMakeArray(int start, int end) throws IOException {
         return Files.lines(inputFilePath)
                .skip(start)
                .map(this::parseLine)
                .limit(end).toArray(BlockWithTransactionCombination[]::new);

    }

    /**
     * generates a combination of blocknumber and transactioncount as a custom datatype from a line
     * @param line the line being parsed
     * @return the combination of blocknumber and transactioncount
     */
    public BlockWithTransactionCombination parseLine(String line) {
        String[] lineparts = line.split(",");
        long blockNumber = Long.parseLong(lineparts[0]);
        int numberOfTransactions = Integer.parseInt(lineparts[1]);
        return new BlockWithTransactionCombination(BigInteger.valueOf(blockNumber), numberOfTransactions);
    }

}
