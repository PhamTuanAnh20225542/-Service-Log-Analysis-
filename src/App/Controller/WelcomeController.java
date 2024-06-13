package App.Controller;

import App.Main;
import javafx.event.ActionEvent;

public class WelcomeController {

	private Main mainApp;
	
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
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
	
}
