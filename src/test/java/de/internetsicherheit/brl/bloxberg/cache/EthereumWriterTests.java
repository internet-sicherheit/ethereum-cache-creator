package de.internetsicherheit.brl.bloxberg.cache;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;


public class EthereumWriterTests {

    @Test
    void canBlockBeWrittenIntoFile(@TempDir Path tempDir) throws IOException {

        Path blockWithTransactions = tempDir.resolve("BlockWithTransactions.txt");
        EthereumWriter blockWriter = CreateWriter(tempDir, "BlockWithTransactions.txt");
        blockWriter.writeBlockWithTransactions(new BlockWithTransactionCombination(BigInteger.valueOf(2000L), 5));

        String currentLine = Files.readAllLines(blockWithTransactions).get(0);
        assertThat(currentLine).isEqualTo("2000,5");
    }
    private EthereumWriter CreateWriter(@TempDir Path tempDir, String filename) {
        return new EthereumWriter(tempDir, filename);
    }

    @Test
    void workWithFiles(){


    }
}
