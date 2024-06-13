package App.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.TreeMap;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;

import Log.LogEntry.IpAddress;
import Log.Parser.ParseAccessLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;


public class IpAddressToCountryName {
    public static int totalVisitors;
    public static int uniqueVisitors;

    private static String imageFilePath = System.getProperty("user.dir") + "\\resources\\FlagDatabase\\";

    @SuppressWarnings("unchecked")
    public static void createTable(TableView<IpAddress> ipTable){

        // Declare all columns match to all attributes
        TableColumn<IpAddress, String> ipColumn = new TableColumn<>("IP Address");
        TableColumn<IpAddress, String> countryColumn = new TableColumn<>("Country Name");
        TableColumn<IpAddress, Integer> frequencyColumn = new TableColumn<>("Number of Requests");
        TableColumn<IpAddress, ImageView> flagColumn = new TableColumn<>("Flag");

        // Set cell value
        ipColumn.setCellValueFactory(new PropertyValueFactory<>("ip"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        frequencyColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfRequests"));
        flagColumn.setCellValueFactory(new PropertyValueFactory<>("image"));

        // Set size
        ipColumn.setMaxWidth(500);
        countryColumn.setMaxWidth(500);
        frequencyColumn.setMaxWidth(200);

        // Add all columns to table
        ipTable.getColumns().addAll(ipColumn, countryColumn, flagColumn, frequencyColumn);
    }

    public static void addData(TableView<IpAddress> ipTable, PieChart pieChart, String targetDate){
        totalVisitors = 0;
        uniqueVisitors = 0;
        String filePath = Config.apacheLogPath;
        File database = new File(System.getProperty("user.dir") + "\\resources\\IpDatabase\\GeoLite2-Country.mmdb");
        TreeMap<String, Integer> tMap = new TreeMap<String, Integer>();
        TreeMap<String, Integer> tMap1 = new TreeMap<String, Integer>();
        ObservableList<IpAddress> ips = FXCollections.observableArrayList();
        ObservableList<PieChart.Data> countryList = FXCollections.observableArrayList();
        try {
            ParseAccessLog parser = new ParseAccessLog();
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (parser.getDate(line).equals(targetDate)){
                    totalVisitors += 1;
                    String ipTemp = parser.getIp(line);
                    if (tMap.containsKey(ipTemp)){
                        tMap.put(ipTemp, tMap.get(ipTemp) + 1);
                    }
                    else{
                        tMap.put(ipTemp, 1);
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        uniqueVisitors = tMap.size();

        for(String ip : tMap.keySet()){
            ips.add(new IpAddress(ip, tMap.get(ip)));
        }

        try{
            DatabaseReader reader = new DatabaseReader.Builder(database).build();
            for(int i = 0; i < ips.size(); i++){
                InetAddress ipAddress = InetAddress.getByName(ips.get(i).getIp());
                CountryResponse response = reader.country(ipAddress);
                String countryName = response.getCountry().getName();
                ips.get(i).setCountryName(countryName);
                ips.get(i).setImage(imageFilePath + ips.get(i).getCountryName() + ".png");
                if (tMap1.containsKey(countryName)) {
                    tMap1.put(countryName, tMap1.get(countryName) + 1);
                }
                else{
                    tMap1.put(countryName, 1);
                }
            }
        }
        catch(IOException | GeoIp2Exception e){ 
            e.printStackTrace();
        }
        for(String name : tMap1.keySet()) {
            countryList.add(new PieChart.Data(name, tMap1.get(name)));
        }
        pieChart.setData(countryList);
        ipTable.setItems(ips);      
    }
}
