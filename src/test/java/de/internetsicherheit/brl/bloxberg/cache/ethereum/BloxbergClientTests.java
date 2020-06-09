package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class BloxbergClientTests {

    @DisplayName("Fetching number of transactions for blocknumber 200 returns 0")
    @CsvSource(
            {"200, 0", "105532, 1 "}
    )
    @ParameterizedTest
    void transactionsInBlock(int blockNumber, int expectedTransactionCount) throws IOException {
        BloxbergClient client = buildClient();
        BigInteger bn = BigInteger.valueOf(blockNumber);
        int blockWithTransactionCount = client.getNumberOfTransactionsInBlock(bn);
        assertThat(blockWithTransactionCount).isEqualTo(expectedTransactionCount);
    }

    @Test
    void labelsAddressesAsUserOrSC() throws IOException {
        BloxbergClient client = buildClient();
        Block block = client.getBlock(BigInteger.valueOf(5036567));
        assertThat(block.getTransactions().get(0).fromAddress.label).isEqualTo(TransactionAddress.Type.USERORVALIDATOR);
        assertThat(block.getTransactions().get(0).toAddress.label).isEqualTo(TransactionAddress.Type.SMARTCONTRACT);
    }

    private BloxbergClient buildClient() {
        return new BloxbergClient("https://core.bloxberg.org");
    }
}
