package de.internetsicherheit.brl.bloxberg.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;


public class ArgParserTest {
    @Test
    void testParser() throws IOException {
        ArgParser parser = new ArgParser();

        String[] args = new String[5];
        
        args[0] = "--url=https://some.kind.of.url";
        args[1] = "--filename=some_output_file";
        args[2] = "--start=13";
        args[3] = "--stop=14";
        args[4] = "--ui=gui";

        String[] parsedArgs = parser.parseArgs(args);


        Assertions.assertEquals("https://some.kind.of.url", parsedArgs[0]);
        Assertions.assertEquals("some_output_file", parsedArgs[1]);
        Assertions.assertEquals("13", parsedArgs[2]);
        Assertions.assertEquals("14", parsedArgs[3]);
        Assertions.assertEquals("gui", parsedArgs[4]);
        
    }


}
