package tn.esprit.controller.Activity;

import tn.esprit.services.ActivitiesService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

public class ActivityStatisticsController implements Initializable {

    @FXML
    private PieChart pieChart;

    private final ActivitiesService activitiesService = new ActivitiesService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Map<String, Integer> statistics = activitiesService.getActivityStateStatistics();
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            statistics.forEach((state, count) -> pieChartData.add(new PieChart.Data(state, count)));
            pieChart.setData(pieChartData);
            pieChart.setTitle("Activity Statistics");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }
}
