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
import javafx.stage.Stage;

/**
 * Entry for the program.
 *
 * @author Jake Botka
 *
 */
public class AppDriver extends Application {
	

	/**
	 * @param args Command Line arguments.
	 */
	public static void main(String[] args) {
		launch();
	}

	/**
	 * Called by launch().
	 * @param arg0 Stage object.
	 * @throws Exception
	 *
	 */
	@Override
	public void start(Stage arg0) throws Exception {
		GameController game = new GameController(arg0);
//		appScene.getStylesheets().add
//		 (Login.class.getResource("Login.css").toExternalForm());

	}

}
