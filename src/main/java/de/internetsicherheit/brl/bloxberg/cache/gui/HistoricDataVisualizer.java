package de.internetsicherheit.brl.bloxberg.cache.gui;

import de.internetsicherheit.brl.bloxberg.cache.BlockAggregator;
import de.internetsicherheit.brl.bloxberg.cache.BlockGroup;
import de.internetsicherheit.brl.bloxberg.cache.HistoricDataExtractor;
import de.internetsicherheit.brl.bloxberg.cache.ethereum.BloxbergClient;
import de.internetsicherheit.brl.bloxberg.cache.persistence.CacheFileReader;
import de.internetsicherheit.brl.bloxberg.cache.persistence.EthereumWriter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


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

        // change to inputfield
        // from current block backwards -  does this make sense?
        int range = 10000;

        // change to inputfield
        int start = 5600437;

        // change to inputfield
        int end = 5342081;

        // change to inputfield
        // max value in file: 5342081, use historicDataExtractor to generate/modify file
        int groupsize = 100000;

        // change to inputfield or dropdown menu
        client = new BloxbergClient(ETHEREUM_NETWORK);
        // add inputfield for the file name.
        writer = new EthereumWriter(Path.of(OUTPUTDIRECTORY), "ExtractedData3.txt");
        BlockAggregator dbs = initDataBlockSummerizer();
        ArrayList<Integer> a= dbs.addGroupTransactions(start,range,10);
        System.out.println(a.get(0));

       /* //extract
        HistoricDataExtractor extractor = new HistoricDataExtractor(client, writer, range);
        *//*extractor.extractAllData();
         TO DO: autoextract = readlastline --> getLastBlocknumberInFile --> extract from lastBlocknumberInFile to
         current blocknumber --> writeNewLines
        *//*

        // visualize
        BlockGroup[] bgA = dbs.addGroupTransactions(start, end, groupsize);

        primaryStage.setTitle("Historic Data Visualizer");
        primaryStage.setScene(generateLineChart(bgA));

        primaryStage.show();*/

    }

    /**
     * generates a LineChart from a BlockGroup Array.
     * @param bgA the Blockgroup Array representing all blocks and their transactions that are being used to generate
     *            the chart
     * @return return the scene that needs to be rendered.
     */
    private Scene generateLineChart(BlockGroup[] bgA) {

        //defining the axes
        final NumberAxis xAxis = new NumberAxis(bgA[0].getStart().doubleValue(),
                bgA[bgA.length-1].getStart().doubleValue() + bgA[bgA.length-1].getRange(), bgA[bgA.length-1].getRange());
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Blocknumbers");
        yAxis.setLabel("Transactions");
        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<>(xAxis,yAxis);

        lineChart.setTitle("Transaction in Blockchain history");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Number of Transactions per " + bgA[bgA.length-1].getRange() + " blocks");

        //populating the series with data  (length -1  ?)
        for(int i = 0; i < bgA.length; i++) {

                series.getData().add(new XYChart.Data(bgA[i].getStart(), bgA[i].getSum()));
        }

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);
        return scene;

    }

    /**
     * creates a DataBlockSummerizer that can add up the transactions per specified range of blocks from a specified path.
     * the range is specified in the method summerizeData(start, end, groupSize)
     * @return the DataBlockSummerizer
     */
    private BlockAggregator initDataBlockSummerizer() {

        Path workDir= Paths.get((OUTPUTDIRECTORY) + "ExtractedData3.txt");
        CacheFileReader cfr = new CacheFileReader(workDir);
        return new BlockAggregator(cfr);
    }

}
