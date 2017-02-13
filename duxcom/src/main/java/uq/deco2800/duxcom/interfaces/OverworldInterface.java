package uq.deco2800.duxcom.interfaces;

import java.io.IOException;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import uq.deco2800.duxcom.overworld.OverworldController;


public class OverworldInterface implements InterfaceSegment{

	@Override
	public boolean loadInterface(Stage primaryStage, String arguments, InterfaceManager interfaceManager) throws IOException {
		URL location = getClass().getResource("/ui/fxml/overworld.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);
		
		Parent root = fxmlLoader.load(location.openStream());
		OverworldController overworldController = fxmlLoader.getController();
		overworldController.setStage(primaryStage);
		overworldController.setInterfaceManager(interfaceManager);
                overworldController.setKeyEvent(interfaceManager);

		InterfaceSegment.showStage(root, primaryStage, interfaceManager);
		primaryStage.setTitle("Overworld");
		
		overworldController.getFocus();
		return true;
	}
	
	@Override
	public void destroyInterface() {
		//would perform cleanup here if there was any
	}
	
}
