package de.internetsicherheit.brl.bloxberg.cache;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class DataBlockSummerizerTest {

    @Test
    void testBlockgroupGeneration() throws IOException {
        DataBlockSummerizer dbs = initDataBlockSummerizer();
        int start = 0;
        int end = 2227;
        int groupSize = 100;
        BlockGroup[] bg = dbs.summerizeData(start, end, groupSize);

        assertThat(bg.length).isEqualTo(end/groupSize + 1);
        assertThat(bg[0].getStart()).isEqualTo(0);
        assertThat(bg[0].getSum()).isBetween(0, 20);
        assertThat(bg[bg.length-1].getStart()).isEqualTo(2200);
        // index out of bounds exception
        // assertThat(bg[bg.length].getStart()).isEqualTo(2300);

    }

    private DataBlockSummerizer initDataBlockSummerizer() {
        Path workDir = Paths.get(Thread.currentThread().getContextClassLoader().getResource("./readfile.txt").getPath());
        return new DataBlockSummerizer(workDir);
    }
}
