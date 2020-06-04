import de.internetsicherheit.brl.bloxberg.cache.gui.BlockDataExtractor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlockDataExtractorTest {

    @DisplayName("does the json-File contain a timestamp? Requires at least 1 transaction.")
    @Test
    void testJsonFile() throws IOException {

        // set up client
        String[] args = new String[5];
        args[0] = "https://core.bloxberg.org";
        args[1] = "timestamps_test";
        args[2] = "0";
        args[3] = "1000";
        BlockDataExtractor bde = new BlockDataExtractor(args);

        // generate Json-File and read it
        bde.generateJsonFile();
        Path outputfile = bde.getOutputfile().toPath();
        List<String> lines = Files.readAllLines(outputfile);

        // check if there is a timestamp in the file
        assertTrue(lines.get(0).contains("timestamp"));

    }
}
