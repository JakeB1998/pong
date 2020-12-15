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
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch();

	}

	@Override
	public void start(Stage arg0) throws Exception {
	
		GameController game = new GameController(arg0);

//		appScene.getStylesheets().add
//		 (Login.class.getResource("Login.css").toExternalForm());

	}

}
