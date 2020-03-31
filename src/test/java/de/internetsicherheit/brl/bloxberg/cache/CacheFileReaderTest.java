package de.internetsicherheit.brl.bloxberg.cache;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class CacheFileReaderTest {



    @DisplayName("A String is converted to a BlockWithTranactionCombination.")
    @Test
    void convertLine() throws IOException {
        CacheFileReader dAgg = CreateFReader();

        String line = "176,0";
        BlockWithTransactionCombination bwtc = dAgg.parseLine(line);

        assertThat(bwtc.transactionCount).isEqualTo(0);
        assertThat(bwtc.blockNumber.intValue()).isEqualTo(BigInteger.valueOf(176).intValue());

    }

    @DisplayName("The CacheFileReader returns a stream of BlockWithTransactionCombinations from readfile which is " +
            "converted to an Array. The Array contains the right blocknumber at an arbitrary index.")
    @Test
    void parseEntireFile() throws IOException {
        CacheFileReader dAgg = CreateFReader();

        Stream<BlockWithTransactionCombination> blockStream = dAgg.readAllLines();
        BlockWithTransactionCombination[] blockTransTuple = blockStream.toArray(BlockWithTransactionCombination[]::new);
        assertThat(blockTransTuple[0].blockNumber.intValue()).isEqualTo(0);
        assertThat(blockTransTuple[2000].blockNumber.intValue()).isEqualTo(2000);
    }

    @DisplayName("Partly parses the file with the stated range.")
    @Test
    void parseFileFromAtoB() throws IOException {
        CacheFileReader dAgg = CreateFReader();
        // file has 2227 lines
        int start = 5;
        int end = 1456;

        Stream<BlockWithTransactionCombination> blockStream = dAgg.readLines(start, end);
        BlockWithTransactionCombination[] limitedBlockArray = blockStream.toArray(BlockWithTransactionCombination[]::new);

        assertThat(limitedBlockArray.length).isLessThan(2227);
        assertThat(limitedBlockArray[0].blockNumber.intValue()).isEqualTo(start);
        assertThat(limitedBlockArray[end-start].blockNumber.intValue()).isEqualTo(end);
        assertThat(limitedBlockArray[542].transactionCount).isBetween(0, 1);

    }
    @Test
    void parseToArray() throws IOException {
        CacheFileReader cfr = CreateFReader();
        int start = 99;
        int end = 100;

        BlockWithTransactionCombination [] bwtcArray = cfr.readLinesAndMakeArray(start, end);

        assertThat(bwtcArray.length).isEqualTo(100);
        assertThat(bwtcArray[0].blockNumber.intValue()).isEqualTo(start);
        assertThat(bwtcArray[bwtcArray.length-1].blockNumber.intValue()).isEqualTo(198);
    }
    private CacheFileReader CreateFReader() throws IOException {
        Path workDir = Paths.get(Thread.currentThread().getContextClassLoader().getResource("./readfile.txt").getPath());
        CacheFileReader dAgg = new CacheFileReader(workDir);
        return dAgg;
    }
}
