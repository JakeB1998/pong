/**
 * File Name: Launcher.java
 * Programmer: Jake Botka
 * Date Created: Dec 16, 2020
 *
 */
package main.org.botka.pong;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Jake Botka
 *
 */
public class Launcher extends Application {


	/**
	 * Called by launch().
	 * @param arg0 Stage object.
	 * @throws Exception
	 *
	 */
	@Override
	public void start(Stage arg0) throws Exception {
		Stage primaryStage = arg0;
		GameController gameController = null;
		File file = new File("/Pong/src/edu/ilstu/ballSprite.png");
		
		try {
			FXMLLoader loader = new FXMLLoader(GameController.class.getResource("PongGame.fxml"));
			Parent page = (Parent)loader.load();
			if (page != null) {
				Scene scene = new Scene(page);
				primaryStage.setScene(scene);
				primaryStage.setFullScreen(false);
				gameController = (GameController)loader.getController();
				if (gameController != null) {
					primaryStage.show();
					gameController.registerScene(scene);
					gameController.initalizeLayout();
					gameController.startGame();
				}
			}
			
			//initialize();

			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
//		appScene.getStylesheets().add
//		 (Login.class.getResource("Login.css").toExternalForm());

	}
}
