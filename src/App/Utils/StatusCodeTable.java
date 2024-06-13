package App.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import Log.LogEntry.StatusCode;
import Log.Parser.ParseAccessLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class StatusCodeTable {


    @SuppressWarnings("unchecked")
    public static void createTable(TableView<StatusCode> statusTable){


        // Create columns
        TableColumn<StatusCode, Integer> statusColumn = new TableColumn<>("Status Code");
        TableColumn<StatusCode, Integer> requetsColumn = new TableColumn<>("Number of Requests");

        // Set cell
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("statusCode"));
        requetsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfRequests"));

        statusTable.getColumns().addAll(statusColumn, requetsColumn);
    }

    public static void addData(TableView<StatusCode> statusTable, String targetDate){
        HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
        String filePath= Config.apacheLogPath;

        try {
            ParseAccessLog parser = new ParseAccessLog();
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            ObservableList<StatusCode> codes = FXCollections.observableArrayList();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if(parser.getDate(line).equals(targetDate)){
                    int statusTemp = parser.getStatusCode(line);
                    if (hm.containsKey(statusTemp)){
                        hm.put(statusTemp, hm.get(statusTemp) + 1);
                    }
                    else{
                        hm.put(statusTemp, 1);
                    }
                }
            }

            for (Integer status : hm.keySet()){
                codes.add(new StatusCode(status, hm.get(status)));
            }

            statusTable.setItems(codes);
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
