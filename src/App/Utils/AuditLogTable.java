package App.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Log.LogEntry.AuditLog;
import Log.Parser.ParseAuditLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AuditLogTable {

    public static String dateTarget;
    public static String ruleTarget;

    @SuppressWarnings("unchecked")
    public static void createTable(TableView<AuditLog> modsecTable) {
        // Declare each columns
        TableColumn<AuditLog, String> typeColumn = new TableColumn<>("Type");
        TableColumn<AuditLog, String> remoteAddColumn = new TableColumn<>("Remote Address");
        TableColumn<AuditLog, String> timeColumn = new TableColumn<>("Timestamp");
        TableColumn<AuditLog, String> requestColumn = new TableColumn<>("Request");
        TableColumn<AuditLog, String> ruleColumn = new TableColumn<>("Triggered Rule");

        // Set cell
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        remoteAddColumn.setCellValueFactory(new PropertyValueFactory<>("remoteAddress"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        requestColumn.setCellValueFactory(new PropertyValueFactory<>("request"));
        ruleColumn.setCellValueFactory(new PropertyValueFactory<>("triggeredRule"));

        modsecTable.getColumns().addAll(typeColumn, remoteAddColumn, timeColumn, requestColumn, ruleColumn);
    }

    public static void addData(TableView<AuditLog> modsecTable) {
        String filePath = Config.modsecLogPath;
        ObservableList<AuditLog> logs = FXCollections.observableArrayList();
        try {
			ParseAuditLog parser = new ParseAuditLog();
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
				if (parser.getDate(line).equals(dateTarget)) {
					if (parser.getTriggeredRule(line).equals(ruleTarget)) {
                        logs.add(parser.parse(line));
                    }
				}         
            }
			modsecTable.setItems(logs);;
			bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
