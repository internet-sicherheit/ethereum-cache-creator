package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;

public class AreTheUncles {

    @DisplayName("Can I haz unclezzzz please?")
    @Test
    void areThereUncles() throws IOException {
        BloxbergClient client = new BloxbergClient("https://core.bloxberg.org");

        int numberOfUncles = 0;
        for (int i = 0; i < 1000000; i++) {
            numberOfUncles += client.getEthBlock(BigInteger.valueOf(i)).getBlock().getUncles().size();

            if (i % 100 == 0) {
                System.out.println("BlockNumber: " + i);
                System.out.println("NumberOfUncles: " + numberOfUncles);
            }
        }
        System.out.println(numberOfUncles);

    }

}
