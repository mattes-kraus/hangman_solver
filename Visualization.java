package hangman_solver;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Here is my Visualization of my HangmanGuesser application
 * @author Mattes
 *
 */
public class Visualization extends Application{ 

	int windowWidth = 1200;
	int windowHeight = 800;
	
	public void start(Stage primaryStage) throws Exception {
		System.out.println("Visualization is running...");
	
	// pre game scene
		BorderPane pregameRoot = new BorderPane();
		Button commitWord = new Button("commit");
		TextField userInput = new TextField("Galgenmaennchen");
		FlowPane flowPane = new FlowPane();
		flowPane.getChildren().addAll(userInput, commitWord);
		flowPane.setMaxHeight(200);
		flowPane.setMaxWidth(userInput.getWidth());
		pregameRoot.setCenter(flowPane);
		Scene preGameScene = new Scene(pregameRoot, windowWidth, windowHeight);
		
	// here was maingame
		
		// Show things
		primaryStage.setTitle("Hangman Guesser");
		primaryStage.setScene(preGameScene);
		
		// Transformation Pre- and Maingame
		commitWord.setOnAction(event -> {
			String userText = userInput.getText();
			if(userText.matches("[a-zA-Z]+"))
			{
				primaryStage.setScene(Utilities.createMainstage(userText.length(), windowWidth, windowHeight));
			}
			else
			{ 
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("Use only valid letters");
				alert.setContentText("please just use a to z! (�=ae, ..., �=ss");

				alert.showAndWait();
			}
			;});
		primaryStage.show();
	}
	
}
