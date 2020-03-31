package de.internetsicherheit.brl.bloxberg.cache;


import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;

public class DataBlockSummerizer {

    // Nummer des ersten Blocks
    private int start;

    // Gesamtanzahl der Blöcke => ende = start + range - 1
    private int range;

    private BlockGroup[] blockGroups;

    private CacheFileReader cfr;

    private Path workDir;

    public DataBlockSummerizer(Path workDir) {
        this.workDir = workDir;
        this.cfr = new CacheFileReader(workDir);

    }

    /**
     * Method that generates an Array of Type Blockgroup, that can be easily used for visualization
     * @param start the blocknumber of the first block to be looked at
     * @param end the blocknumber of the last block to be looked at
     * @param groupSize the Size of groups of blocks.
     * @return an Array of Blockgroups
     * @throws IOException exception when file cannot be read
     */
        public BlockGroup[] summerizeData(int start, int end , int groupSize) throws IOException {


            // different ArraySize required depending on groupSize and start/endpoint
            if((end - start) % groupSize == 0) {
                blockGroups = new BlockGroup[(end - start)/groupSize];
            } else {
                blockGroups = new BlockGroup[(end - start)/groupSize +1];
            }

            // iterates using a stream, creates groups of blocks by adding up the number of transitions
            int currentBlockNumber = start;
            int currentIndex = 0;
            while(currentBlockNumber < end){

                // creating an Array of BlockWithTransactionCombination py parsing lines from the stream
                BlockWithTransactionCombination[] blocks = this.cfr.readLinesAndMakeArray(currentBlockNumber, groupSize);

                // adding up the transitions
                int sum = 0;
                for (int i = 0; i < blocks.length; i++){
                    sum = sum + blocks[i].transactionCount;
                }
                // creating the actual blockgroup objects
                this.blockGroups[currentIndex] = new BlockGroup(BigInteger.valueOf(currentBlockNumber), sum, groupSize);
                System.out.println("New Blockgroup \n" + "Blocknumber of starting block: " + BigInteger.valueOf(currentBlockNumber)
                        + "\n" + "Sum of all Transactions for this Blockgroup: " + sum);

                currentIndex++;
                currentBlockNumber = currentBlockNumber + groupSize;

        }

       return blockGroups;

    }


}
