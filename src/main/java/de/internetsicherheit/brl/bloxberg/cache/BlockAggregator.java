package de.internetsicherheit.brl.bloxberg.cache;


import de.internetsicherheit.brl.bloxberg.cache.ethereum.BlockWithTransactionCombination;
import de.internetsicherheit.brl.bloxberg.cache.persistence.CacheFileReader;

import java.io.IOException;
import java.math.BigInteger;

public class BlockAggregator {


    private BlockGroup[] blockGroups;

    private CacheFileReader cfr;



    public BlockAggregator(CacheFileReader cfr) {
        // instead use interface that provides BlockWithTransactionsCombinations
        this.cfr = cfr;

    }

    /**
     * Method that generates an Array of Type Blockgroup, that can be easily used for visualization
     * @param start the blocknumber of the first block to be looked at
     * @param end the blocknumber of the last block to be looked at
     * @param groupSize the Size of groups of blocks.
     * @return an Array of Blockgroups
     * @throws IOException exception when file cannot be read
     */
        public BlockGroup[] aggregate(int start, int end , int groupSize) throws IOException {


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
