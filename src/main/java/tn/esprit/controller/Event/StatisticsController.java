package tn.esprit.controller.Event;

import tn.esprit.services.EventsService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    @FXML
    private PieChart pieChart;

    @FXML
    private Label seasonLabel;

    private final EventsService eventsService = new EventsService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Map<String, Integer> statistics = eventsService.getSeasonStatistics();
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            statistics.forEach((season, count) -> pieChartData.add(new PieChart.Data(season, count)));
            pieChart.setData(pieChartData);
            pieChart.setTitle("Season Statistics");

            // Find the most common season
            String mostCommonSeason = findMostCommonSeason(statistics);
            seasonLabel.setText("The most common season for events is " + mostCommonSeason + ".");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    private String findMostCommonSeason(Map<String, Integer> statistics) {
        // Find the season with the maximum count
        int maxCount = 0;
        String mostCommonSeason = "";
        for (Map.Entry<String, Integer> entry : statistics.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommonSeason = entry.getKey();
            }
        }
        return mostCommonSeason;
    }
}
