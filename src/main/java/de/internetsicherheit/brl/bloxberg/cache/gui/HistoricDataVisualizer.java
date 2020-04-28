package de.internetsicherheit.brl.bloxberg.cache.gui;

import de.internetsicherheit.brl.bloxberg.cache.BlockAggregator;
import de.internetsicherheit.brl.bloxberg.cache.BlockGroup;
import de.internetsicherheit.brl.bloxberg.cache.ethereum.BloxbergClient;
import de.internetsicherheit.brl.bloxberg.cache.persistence.CacheFileReader;
import de.internetsicherheit.brl.bloxberg.cache.persistence.EthereumWriter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;


public class HistoricDataVisualizer extends Application {

    private BloxbergClient client;
    private EthereumWriter writer;
    private final String ETHEREUM_NETWORK = "https://core.bloxberg.org";
    private final String OUTPUTDIRECTORY = System.getProperty("user.dir") + "/output/";



    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws IOException {

        // setup

        // should not show up in gui
        String filename = "ExtractedData5.txt";
        String filename2 = "extractedData6.json";
        // change to inputfield
        // from current block backwards -  does this make sense?
        int limit = 10000;

        // change to inputfield
        int start = 0;

        // change to inputfield
        int end = 5342081;

        // change to inputfield
        // max value in file: 5342081, use historicDataExtractor to generate/modify file
        int groupsize = 1000;

        // change to inputfield or dropdown menu
        client = new BloxbergClient(ETHEREUM_NETWORK);
        // add inputfield for the file name.
        // original: writer = new EthereumWriter(Path.of(OUTPUTDIRECTORY), filename);
        writer = new EthereumWriter(Path.of(OUTPUTDIRECTORY), filename);
        BlockAggregator dbs = initDataBlockSummerizer(filename);
        TestCenter testCenter = new TestCenter(client, new File(OUTPUTDIRECTORY + "extractedData6.json"));
        testCenter.generateJsonFile(0, 10000);

        //extract (limit not used atm)
        //HistoricDataExtractor extractor = new HistoricDataExtractor(client, writer, limit);
        //extractor.extractDataWithRange(0, 1);
        /*TO DO: autoextract = readlastline --> getLastBlocknumberInFile --> extract from lastBlocknumberInFile to
        current blocknumber --> writeNewLines*/


         //visualize

        //ArrayList<BlockGroup> blockGroups = dbs.addGroupTransactions(start, limit,groupsize);

        //primaryStage.setTitle("Historic Data Visualizer");
        //primaryStage.setScene(generateLineChart(blockGroups));

        //primaryStage.show();


    }

    /**
     * generates a LineChart from a BlockGroup Array.
     * @param blockGroups the Blockgroup ArrayList representing all blocks and their transactions that are being used to generate
     *            the chart
     * @return return the scene that needs to be rendered.
     */
    private Scene generateLineChart(ArrayList<BlockGroup> blockGroups) {

        //defining the axes
        final NumberAxis xAxis = new NumberAxis(blockGroups.get(0).getStart().doubleValue(), blockGroups.get(blockGroups.size()-1).getStart().doubleValue() + blockGroups.get(blockGroups.size()-1).getRange(),
        blockGroups.get(blockGroups.size()-1).getRange());
        /*final NumberAxis xAxis = new NumberAxis(bgA[0].getStart().doubleValue(),
                bgA[bgA.length-1].getStart().doubleValue() + bgA[bgA.length-1].getRange(), bgA[bgA.length-1].getRange());*/
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Blocknumbers");
        yAxis.setLabel("Transactions");
        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<>(xAxis,yAxis);

        lineChart.setTitle("Transaction in Blockchain history");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Number of Transactions per " + blockGroups.get(blockGroups.size()-1).getRange() + " blocks");
        Iterator it = blockGroups.iterator();
        while(it.hasNext()) {
            BlockGroup currentBlock = (BlockGroup)it.next();
            series.getData().add(new XYChart.Data(currentBlock.getStart(), currentBlock.getSum()));

            System.out.println("summe der Transaktionen pro 1000 blöcke: " +currentBlock.getSum());
        }
        //populating the series with data  (length -1  ?)
        /*for(int i = 0; i < bgA.length; i++) {

                series.getData().add(new XYChart.Data(bgA[i].getStart(), bgA[i].getSum()));
        }*/

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);
        return scene;

    }

    /**
     * creates a DataBlockSummerizer that can add up the transactions per specified range of blocks from a specified path.
     * the range is specified in the method summerizeData(start, end, groupSize)
     * @return the DataBlockSummerizer
     */
    private BlockAggregator initDataBlockSummerizer(String filename) {

        Path workDir= Paths.get((OUTPUTDIRECTORY) + filename);
        System.out.println("workdir: " + workDir);
        CacheFileReader cfr = new CacheFileReader(workDir);
        return new BlockAggregator(cfr);
    }

}
