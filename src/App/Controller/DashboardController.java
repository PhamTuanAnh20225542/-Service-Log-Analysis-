package App.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import App.Main;
import App.Utils.IpAddressToCountryName;
import App.Utils.LogLineChart;
import App.Utils.AccessLogTable;
import App.Utils.AuditLogTable;
import App.Utils.ModsecRuleTable;
import App.Utils.StatusCodeTable;
import Log.LogEntry.*;

public class DashboardController implements Initializable {
	
	private Main mainApp;

	@FXML
	LineChart<String, Number> linechart;
	
	@FXML
	PieChart piechart;
	
	@FXML
	TableView<IpAddress> iptable;

	@FXML
	TableView<Rule> ruletable;

	@FXML
	TableView<StatusCode> statustable;

	@FXML
	Button refreshButton;

	@FXML
	Label totalVisitors;

	@FXML
	Label uniqueVisitors;

	@FXML
	DatePicker datePicker;
	
	public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
	
	public void switchToWelcome(ActionEvent event) {
		mainApp.switchToWelcome();
	}
	
	public void switchToDashboard(ActionEvent event) {
		mainApp.switchToDashboard();
	}

	public void switchToStream(ActionEvent event) {
		mainApp.switchToStream();
	}

	public void switchToExplorer(ActionEvent event) {
		mainApp.switchToExplorer("", "");
	}

	public void refreshDashboard(ActionEvent event) {
		String targetDate = datePicker.getValue().toString();
		AccessLogTable.dateTarget = targetDate;
		AuditLogTable.dateTarget = targetDate;

		ruletable.setItems(FXCollections.observableArrayList());
		iptable.setItems(FXCollections.observableArrayList());
		piechart.setData(FXCollections.observableArrayList());
		statustable.setItems(FXCollections.observableArrayList());
		linechart.getData().clear();

		IpAddressToCountryName.addData(iptable, piechart, targetDate);
		totalVisitors.setText(Integer.toString(IpAddressToCountryName.totalVisitors));
		uniqueVisitors.setText(Integer.toString(IpAddressToCountryName.uniqueVisitors));

		LogLineChart.addData(linechart, targetDate);
		setupActionToLineChart();
		StatusCodeTable.addData(statustable, targetDate);
		ModsecRuleTable.addData(ruletable, targetDate);
	}

	private void setupIpTableAndPieChart(){
		IpAddressToCountryName.createTable(iptable);
		IpAddressToCountryName.addData(iptable, piechart, datePicker.getValue().toString());
		iptable.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				if (iptable.getSelectionModel().getSelectedItem() != null){
					AccessLogTable.ipTarget = iptable.getSelectionModel().getSelectedItem().getIp();
					mainApp.switchToExplorer("IP", "Apache");
				}
			}
		});
		totalVisitors.setText(Integer.toString(IpAddressToCountryName.totalVisitors));
		uniqueVisitors.setText(Integer.toString(IpAddressToCountryName.uniqueVisitors));
	}

	private void setupStatusTable() {
		StatusCodeTable.createTable(statustable);
		StatusCodeTable.addData(statustable,datePicker.getValue().toString());
		statustable.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				if (statustable.getSelectionModel().getSelectedItem() != null){
					AccessLogTable.statusTarget = statustable.getSelectionModel().getSelectedItem().getStatusCode();
					mainApp.switchToExplorer("Status", "Apache");
				}
			}
		});
	}

	private void setupDatePicker() {
		datePicker.setValue(LocalDate.now());
		AccessLogTable.dateTarget = datePicker.getValue().toString();
		AuditLogTable.dateTarget = datePicker.getValue().toString();
	}

	private void setupLineChart() {
		linechart.setTitle("Log Line Chart");
		LogLineChart.addData(linechart,  datePicker.getValue().toString());
		setupActionToLineChart();
	}

	private void setupActionToLineChart() {
		ObservableList<LineChart.Series<String, Number>> seriesList = linechart.getData();
		for(Series<String, Number> series : seriesList){
			ObservableList<XYChart.Data<String, Number>> datas = series.getData();
			for (XYChart.Data<String, Number> data : datas){
				data.getNode().setOnMouseClicked(e -> {
					if ((!e.isControlDown()) && (e.getClickCount() == 2)) {
						AccessLogTable.hourTarget = Integer.parseInt(data.getXValue());
						mainApp.switchToExplorer("Hour", "Apache");
					}
				});
			}
		}
	}
	
	private void setupRuleTable(){
		ModsecRuleTable.createTable(ruletable);
		ModsecRuleTable.addData(ruletable, datePicker.getValue().toString());
		ruletable.setOnMouseClicked(e -> {
			if ((!e.isControlDown()) && (e.getClickCount() == 2)) {
				AuditLogTable.ruleTarget = ruletable.getSelectionModel().getSelectedItem().getTriggeredRule();
				mainApp.switchToExplorer("", "Modsec");
			}
		});
	}

	public void trigger() {
		setupDatePicker();
		setupIpTableAndPieChart();
		setupLineChart();
		setupStatusTable();
		setupRuleTable();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
}
