package de.internetsicherheit.brl.bloxberg.cache;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class HistoricDataExtractorTest {


    @DisplayName("retry works")
    @Test
    void canExtractBlockWithRetry() throws IOException {
        BloxbergClient mockClient = mock(BloxbergClient.class);
        HistoricDataExtractor extractor = new HistoricDataExtractor(mockClient, null, 0);
        int blockNumber = 1000;

        when(mockClient.getNumberOfTransactionsInBlock(BigInteger.valueOf(blockNumber)))
                .thenThrow(new IOException())
                .thenReturn(42);

        assertThat(extractor.getNumberOfTransactionsInBlockWithRetry(blockNumber)).isEqualTo(42);
    }

    @DisplayName("default value of -1 in case of retries exceeded")
    @Test
    void defaultValueIfRetryFails() throws IOException {
        BloxbergClient mockClient = mock(BloxbergClient.class);
        HistoricDataExtractor extractor = new HistoricDataExtractor(mockClient, null, 0);
        int blockNumber = 1000;

        when(mockClient.getNumberOfTransactionsInBlock(BigInteger.valueOf(blockNumber)))
                .thenThrow(new IOException());

        assertThat(extractor.getNumberOfTransactionsInBlockWithRetry(blockNumber)).isEqualTo(-1);
    }
}
