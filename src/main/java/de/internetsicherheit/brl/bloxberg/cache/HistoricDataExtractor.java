package de.internetsicherheit.brl.bloxberg.cache;

import org.rnorth.ducttape.RetryCountExceededException;
import org.rnorth.ducttape.unreliables.Unreliables;

import java.io.IOException;
import java.math.BigInteger;

import static java.util.stream.IntStream.range;

public class HistoricDataExtractor {

    private BloxbergClient client;
    private EthereumWriter writer;
    private final int range;


    public HistoricDataExtractor(BloxbergClient client, EthereumWriter writer, int range) {

        this.client = client;
        this.writer = writer;
        this.range = range;

    }

    /**
     * Extract Data from the Client ranging from the most recent block to a past block specified by a range
     *
     * @throws IOException exception when connection to blockchain cannot be established
     */
    public void extractData() throws IOException {

        range(client.getCurrentBlockNumber().intValue() - range, client.getCurrentBlockNumber().intValue())
                .mapToObj(i -> new BlockWithTransactionCombination(BigInteger.valueOf(i), getNumberOfTransactionsInBlockWithRetry(i)))
                .forEach(this::writeBlock);
    }

    /**
     * Extract all the Number of Transactions per block for the entire history of the blockchain. this might take a while.
     *
     * @throws IOException exception when connection to blockchain cannot be established
     */
    public void extractAllData() throws IOException {
        range(0, client.getCurrentBlockNumber().intValue())
                .mapToObj(i -> new BlockWithTransactionCombination(BigInteger.valueOf(i), getNumberOfTransactionsInBlockWithRetry(i)))
                .forEach(this::writeBlock);
    }

    /**
     * Extract the amount of transactions from a single block.
     *
     * If the program can't establish connection to the
     * network it retries it up to 10 times.
     *
     * @param blockNumber the number of the block that the method extracts data from
     * @return amount of transactions in a single block
     */
    public int getNumberOfTransactionsInBlockWithRetry(int blockNumber) {
        try {
            return Unreliables.retryUntilSuccess(10, () ->
                    client.getNumberOfTransactionsInBlock(BigInteger.valueOf(blockNumber))
            );
        } catch (RetryCountExceededException e) {
            return -1;
        }
    }


    /**
     * this method merely tells the writer to write out
     * a combination of blocknumber and amount of transactions into a file specified in the writer
     *
     * @param comb the combination of blocknumber and transactioncount as a custom datatype.
     */
    private void writeBlock(BlockWithTransactionCombination comb) {
        try {
            writer.writeBlockWithTransactions(comb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
