/*
 * File name:  AppDriver.java
 *
 * Programmer : Jake Botka
 *
 * Date: Sep 9, 2019
 */

package main.org.botka.pong;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * <insert class description here>
 *
 * @author Jake Botka
 *
 */
public class AppDriver extends Application {
	static Stage primaryStage;
	static Scene appScene;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch();

	}

	@Override
	public void start(Stage arg0) throws Exception {
		primaryStage = arg0;

		GameController game = new GameController();

//		appScene.getStylesheets().add
//		 (Login.class.getResource("Login.css").toExternalForm());

	}

}
