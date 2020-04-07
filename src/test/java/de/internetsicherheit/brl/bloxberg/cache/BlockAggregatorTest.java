package de.internetsicherheit.brl.bloxberg.cache;

import de.internetsicherheit.brl.bloxberg.cache.persistence.CacheFileReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BlockAggregatorTest {
    // TODO: write test for aggregating transactions with stub
    @Test
    void testBlockgroupGeneration() throws IOException {
        BlockAggregator dbs = initDataBlockSummerizer();
        int start = 0;
        int end = 2227;
        int groupSize = 100;
//        BlockGroup[] bg = dbs.addGroupTransactions(start, end, groupSize);
//
//        assertThat(bg.length).isEqualTo(end/groupSize + 1);
//        assertThat(bg[0].getStart()).isEqualTo(0);
//        assertThat(bg[0].getSum()).isBetween(0, 20);
//        assertThat(bg[bg.length-1].getStart()).isEqualTo(2200);
        // index out of bounds exception
        // assertThat(bg[bg.length].getStart()).isEqualTo(2300);

    }

    private BlockAggregator initDataBlockSummerizer() {
        Path workDir = Paths.get(Thread.currentThread().getContextClassLoader().getResource("./readfile.txt").getPath());
        CacheFileReader cfr = new CacheFileReader(workDir);
        return new BlockAggregator(cfr);
    }
}
